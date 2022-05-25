 
package portfolios.security.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import portfolios.dto.Message;
import portfolios.email.EmailService;
import portfolios.security.dto.LoginResponse;
import portfolios.security.dto.LoginUser;
import portfolios.security.dto.NewPassword;
import portfolios.security.dto.NewUser;
import portfolios.security.entity.RecoveryPass;
import portfolios.security.entity.User;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.registration.RegistrationService;
import portfolios.security.service.RecoveryPassService;
import portfolios.security.service.RoleService;
import portfolios.security.service.UserService;



@RestController
@CrossOrigin(origins = "https://portfolioap-102b7.web.app")
public class AuthController {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    UserService userService;
    
    @Autowired
    RoleService rolService;
    
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    RegistrationService registrationService;
    
    @Autowired
    RecoveryPassService recoveryPassService;
    
    @Autowired
    EmailService emailService;
    


   
    
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
    
    
         if(bindingResult.hasErrors()){
            
                    return new ResponseEntity<>(new Message("Error in fields", "/auth/login"), HttpStatus.BAD_REQUEST);
            }
         
         Authentication authentication = 
                        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
         
         SecurityContextHolder.getContext().setAuthentication(authentication);
         
         User user = userService.getByUsername(loginUser.getUsername());
         
         String jwt = jwtProvider.generateToken(authentication, user.getId());
        
         
         return  new ResponseEntity<>(new LoginResponse(jwt,user.getId()), HttpStatus.ACCEPTED);
     
        
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> signUp(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
    
            if(bindingResult.hasErrors()){
            
                    return new ResponseEntity(new Message("Error in fields", "/auth/register"), HttpStatus.BAD_REQUEST);
            }
            
            if(userService.existsByUsername(newUser.getUsername())){
            
                    return new ResponseEntity(new Message("username already exists", "/auth/register"), HttpStatus.BAD_REQUEST);
            
            }
            
            if(userService.existsByEmail(newUser.getEmail())){
            
                    return new ResponseEntity(new Message("email already exists", "/auth/register"), HttpStatus.BAD_REQUEST);
            
            }
            
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            
            userService.signUpUser(newUser);
            
            return new ResponseEntity(new Message("succeful sign up", "/auth/register"), HttpStatus.CREATED);
    }
    
    
    @GetMapping("/auth/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token){
        
            try{
                 
                registrationService.confirmToken(token);
                
                return new ResponseEntity(new Message("email confirmed", "/auth/confirm"), HttpStatus.ACCEPTED);
                 
             } catch(IllegalStateException e){
             
                 return new ResponseEntity(new Message("error: " + e.getMessage(), "/auth/confirm"), HttpStatus.BAD_REQUEST);
             
             }
                           
    }
    
    @PostMapping("/auth/recovery")
    public ResponseEntity<?> generateRecoveryPass(@RequestBody String email){
        
        System.out.println("email: "+ email);
    
        try{
        
        User user = userService.getByEmail(email);
        
        if(user == null){
        
              return new ResponseEntity(new Message("Email not found", "/auth/recoverypass"), HttpStatus.BAD_REQUEST);

        }
        
        RecoveryPass oldRecovery = null;

        if(user.getRecovery() != null){
        
             oldRecovery = user.getRecovery();
             
        }

        RecoveryPass recoveryPass = recoveryPassService.createAndSaveCode(user);
        
        System.out.println("recovery controller: "+ recoveryPass);
        
        recoveryPass.setUser(user);
        
        user.setRecovery(recoveryPass);
        
        userService.editUser(user);
        
        if(oldRecovery != null){
        
            recoveryPassService.deleteCode(oldRecovery);
        
        }

        emailService.sendCode(user.getEmail(), user.getUsername(), String.valueOf(recoveryPass.getToken()));
        
        return new ResponseEntity(new Message("Código de recuperación enviado correctamente al email", "/auth/recoverypass"), HttpStatus.OK);
        
        } catch(IllegalStateException error){
        
            return new ResponseEntity(new Message("error"+ error.getMessage(), "/auth/recoverypass"), HttpStatus.BAD_REQUEST);
        }
    
    }
    
    @PostMapping("/auth/checkcode")
    public ResponseEntity<?> recoveryPass(@RequestBody String code){
    
        try{
                 
                recoveryPassService.confirmCode(Integer.parseInt(code));
                
                return new ResponseEntity(new Message("code confirmed", "/auth/recoverypass"), HttpStatus.ACCEPTED);
                 
             } catch(IllegalStateException e){
             
                 return new ResponseEntity(new Message("error: " + e.getMessage(), "/auth/recoverypass"), HttpStatus.BAD_REQUEST);
             
             }
    
    }
    
    
    @PostMapping("/auth/newpassword")
    public ResponseEntity<?> setNewPassword(@Valid @RequestBody NewPassword newPassword, BindingResult bindingResult){
    
        if(bindingResult.hasErrors()){
            
                    return new ResponseEntity(new Message("Error in fields", "/auth/recoverypass"), HttpStatus.BAD_REQUEST);
            }
        
        try{
            
            RecoveryPass recoveryPass = recoveryPassService.getCode(newPassword.getCode());
            
            if(recoveryPass == null){
            
                return new ResponseEntity(new Message("el código no existe", "/auth/recoverypass"), HttpStatus.BAD_REQUEST);
            }
            
            User user = recoveryPass.getUser();
            
            user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
            
            user.setRecovery(null);
            
            userService.editUser(user);
            
            recoveryPassService.deleteCode(recoveryPass);
            
            return new ResponseEntity(new Message("el password ha sido cambiado correctamente", "/auth/recoverypass"), HttpStatus.OK);
            
        }catch(IllegalStateException error){
        
            error.printStackTrace();
            
            return new ResponseEntity(new Message("error: "+ error.getMessage(), "/auth/recoverypass"), HttpStatus.BAD_REQUEST);
        
        }
            
    }
}
