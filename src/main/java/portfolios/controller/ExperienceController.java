
package portfolios.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import portfolios.dto.ExperienceDto;
import portfolios.dto.Message;
import portfolios.entity.Experience;
import portfolios.entity.Person;
import portfolios.security.entity.User;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.service.UserService;
import portfolios.service.ExperienceService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ExperienceController {
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    ExperienceService experienceService;
    
    @Autowired
    UserService userService;
    
    
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/user/experience")
    public ResponseEntity<?> createExperience( @RequestHeader("Authorization") String authorization,
                                     @Valid @RequestBody  ExperienceDto experienceDto, BindingResult bindingResult, HttpServletResponse res
                                                           ){
    
                if(bindingResult.hasErrors()){
                
                    res.setStatus(400);
                    return new ResponseEntity(new Message("error fields", "user/experience"), HttpStatus.BAD_REQUEST);
                    
                }
                
                    String username = jwtProvider.getUsernameFromToken(authorization);

                    User user = userService.getByUsername(username);
                    
                    Person person = user.getPerson();
                    
                    try {
                        
                        experienceService.create(person, experienceDto);
                        List<Experience> experiences = experienceService.getExperiences(username);
                        
                        return new ResponseEntity(experiences, HttpStatus.CREATED);
                        
                    } catch(Exception e){
                    
                       res.setStatus(400);
                       return new ResponseEntity(new Message("error in fields", "user/experience"), HttpStatus.BAD_REQUEST);
                    
                    }
                
                    
                          
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/api/v1/user/experience")
    public ResponseEntity<?> editExperience(@RequestHeader("Authorization") String authorization,
                                            @Valid @RequestBody List<ExperienceDto> experienceDto, BindingResult bindingResult){
    
           if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "/user/experience"), HttpStatus.BAD_REQUEST);

               }
           
             String username = jwtProvider.getUsernameFromToken(authorization);
             
             Person person = userService.getByUsername(username).getPerson();
             
             try{
                 
                  if(experienceService.edit(person, experienceDto)){
            
                    return new ResponseEntity(new Message("edited experience", "user/experience"), HttpStatus.OK);
            
            } else {
            
                
                    return new ResponseEntity(new Message("id error", "user/experience"), HttpStatus.BAD_REQUEST);
            
            }
             
             }catch(Exception e){
             
             
                 return new ResponseEntity(new Message("error: "+ e.getMessage(), "user/experience"), HttpStatus.BAD_REQUEST);
                 
             }
                
           
       
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/api/v1/user/experience/{id}")
    public Message deleteExperience(@RequestHeader("Authorization") String authorization, @PathVariable("id") String id, HttpServletResponse res ){
        
         System.out.println("id: "+ id);
    
         String username = jwtProvider.getUsernameFromToken(authorization);
         
         Person person = userService.getByUsername(username).getPerson();
         
         if(experienceService.delete(person, Integer.parseInt(id))){
         
              res.setStatus(200);
              return new Message("Deleted experience", "/user/experience");
         
         }
    
             res.setStatus(400);
             return new Message("Bad request", "/user/experience");
         
    }
    
    @GetMapping("/api/v1/{username}/experience")
    public ResponseEntity<?> getExperiences(@PathVariable String username){
    
            try{
        
            List<Experience> experiences = experienceService.getExperiences(username);
                   
            return new ResponseEntity(experiences, HttpStatus.OK);
       
        } catch (Exception e) {
        
        
                      return new ResponseEntity("user not found", HttpStatus.BAD_REQUEST);
            
        }
    }
   
    
}
