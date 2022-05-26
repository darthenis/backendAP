
package portfolios.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import portfolios.dto.Message;
import portfolios.dto.PrivateMessagesDto;
import portfolios.entity.Person;
import portfolios.entity.PrivateMessages;
import portfolios.security.entity.User;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.recaptcha.ReCaptchaResponse;
import portfolios.security.service.CaptchaService;
import portfolios.security.service.UserService;
import portfolios.service.PrivateMessagesService;

@RestController
public class PrivateMessagesController {
    
    @Autowired
    PrivateMessagesService privateMessagesService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    CaptchaService captchaService;
    
    @PostMapping("/api/v1/service/message")
    public ResponseEntity<?> createMessage(@Valid @RequestBody  PrivateMessagesDto privateMessagesDto, 
                                             BindingResult bindingResult,
                                             @RequestParam(name = "g-recaptcha-response") String response){
                
                ReCaptchaResponse reCaptchaResponse = captchaService.verify(response);
        
                if(!reCaptchaResponse.isSuccess()){
                
                   return new ResponseEntity(new Message("Error ReCaptcha: "+ reCaptchaResponse.getErrorCodes(), "/service/messages"), HttpStatus.BAD_REQUEST);
                       
                }
                
                if(bindingResult.hasErrors() | privateMessagesDto.getUsername() == null){
                
                    return new ResponseEntity(new Message("Error in fields", "/user/messages"), HttpStatus.BAD_REQUEST);
                    
                }
                
                Person person = userService.getByUsername(privateMessagesDto.getUsername()).getPerson();
                
                try{
                
                    privateMessagesService.create(person, privateMessagesDto);
                    
                    return new ResponseEntity(new Message("Created succesful", "user/messages"), HttpStatus.CREATED);
                
                } catch(Exception e){
                
                    e.printStackTrace();
                    
                    return new ResponseEntity(new Message("Error: "+ e.getMessage(), "user/messages"), HttpStatus.INTERNAL_SERVER_ERROR);
                
                }
                
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/api/v1/service/messages")
    public ResponseEntity<?> editSeen(  @RequestHeader("Authorization") String authorization, 
                                        @Valid @RequestBody  PrivateMessagesDto privateMessagesDto, BindingResult bindingResult){
    
                if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "/service/messages"), HttpStatus.BAD_REQUEST);
                    
                }
                
                
                String username = jwtProvider.getUsernameFromToken(authorization);

                    User user = userService.getByUsername(username);
                    
                    Person person = user.getPerson();
                    
                    try {
                        
                        privateMessagesService.editSeen(person, privateMessagesDto);
                       
                        
                        return new ResponseEntity(new Message("Edit succesful", "/service/messages"), HttpStatus.CREATED);
                        
                    } catch(Exception e){
                    
                       e.printStackTrace();
                    
                       return new ResponseEntity(new Message("error in fields", "/service/messages"), HttpStatus.INTERNAL_SERVER_ERROR);
                    
                    }

    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/api/v1/service/message/{id}")
    public ResponseEntity<?> deleteExperience(@RequestHeader("Authorization") String authorization, @PathVariable("id") String id, HttpServletResponse res ){

         String username = jwtProvider.getUsernameFromToken(authorization);
         
         Person person = userService.getByUsername(username).getPerson();
         
         if(privateMessagesService.delete(person, Integer.parseInt(id))){
         
            
              return new ResponseEntity(new Message("Deleted experience", "/user/experience"), HttpStatus.OK);
         
         }
    
             res.setStatus(400);
             return new ResponseEntity(new Message("Bad request", "/user/experience"), HttpStatus.BAD_REQUEST);
         
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/v1/service/messages")
    public ResponseEntity<?> getExperiences(@RequestHeader("Authorization") String authorization){
        
            String username = jwtProvider.getUsernameFromToken(authorization);

            Person person = userService.getByUsername(username).getPerson();
    
            try{
        
            List<PrivateMessages> privateMessages = privateMessagesService.findByPerson(person);
                   
            return new ResponseEntity(privateMessages, HttpStatus.OK);
       
        } catch (Exception e) {
            
              e.printStackTrace();
        
              return new ResponseEntity("user not found", HttpStatus.BAD_REQUEST);
            
        }
    }
    
}
