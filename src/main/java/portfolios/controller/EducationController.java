
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
import portfolios.dto.EducationDto;
import portfolios.dto.Message;
import portfolios.entity.Education;
import portfolios.entity.Person;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.service.UserService;
import portfolios.service.EducationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EducationController {
    
    @Autowired
    EducationService educationService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/user/education")
    public ResponseEntity<?> createEducation(@RequestHeader("Authorization") String authorization,
                                   @Valid @RequestBody EducationDto educationDto, BindingResult bindingResult, HttpServletResponse res){
    
            if(bindingResult.hasErrors()){
                            
                        return new ResponseEntity(new Message("Error in fields", "user/education"), HttpStatus.BAD_REQUEST);

                   }
           

             String username = jwtProvider.getUsernameFromToken(authorization);
             
             Person person = userService.getByUsername(username).getPerson();
             
             try{
             
                 educationService.create(person, educationDto);
                 
                 List<Education> educations = educationService.getEducations(username);

                 return new ResponseEntity(educations, HttpStatus.CREATED);
             
             }catch(Exception e){
             
         
                 return new ResponseEntity(new Message("Error in creation", "user/education"), HttpStatus.INTERNAL_SERVER_ERROR);
            
             }
             
             
    
    }
            
         
    
    
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/api/v1/user/education")
    public ResponseEntity<?> editEducation( @RequestHeader("Authorization") String authorization,
                                            @Valid @RequestBody  List<EducationDto> educationDto, BindingResult bindingResult
                                                           ){
    
                if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "user/person/education"), HttpStatus.BAD_REQUEST);
                    
                }
                
                String username = jwtProvider.getUsernameFromToken(authorization);
                
                Person person = userService.getByUsername(username).getPerson();
                
                if(educationService.edit(person, educationDto)){
                
                     return new ResponseEntity(new Message("edit sucessful", "/user/education"), HttpStatus.OK);
                
                } else {
                
                         return new ResponseEntity(new Message("error internal", "/user/education"), HttpStatus.BAD_REQUEST);
                                   
                }
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/api/v1/user/education/{id}")
    public Message deleteEducation(@RequestHeader("Authorization") String authorization,
                                   @PathVariable("id") int id, HttpServletResponse res){
    
         
            String username = jwtProvider.getUsernameFromToken(authorization);
         
            Person person = userService.getByUsername(username).getPerson();
 
            if(educationService.delete(person, id)){

                        res.setStatus(200);
                        return new Message("project deleted succesful", "user/education");
                         
         }
    
                        res.setStatus(400);
                        return new Message("id not found", "user/education");
         
    }
    
    
    @GetMapping("/api/v1/{username}/education")
    public ResponseEntity<?> getEducations(@PathVariable String username){
           
        
        try{
        
            List<Education> educations = educationService.getEducations(username);
            
            return new ResponseEntity(educations, HttpStatus.OK);
        
        
        } catch (Exception e) {
        
        
                      return new ResponseEntity("user not found", HttpStatus.BAD_REQUEST);
            
        }
            
    }
    
}
