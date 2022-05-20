
package portfolios.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import portfolios.dto.Message;
import portfolios.dto.PersonDto;
import portfolios.entity.Person;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.service.UserService;
import portfolios.service.PersonService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PersonController {
    
    @Autowired
    PersonService personService;
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    UserService userService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/api/v1/user/person")
    public ResponseEntity<?> editPerson( @RequestHeader("Authorization") String authorization,
                                         @Valid @RequestBody  PersonDto personDto, BindingResult bindingResult
                                                           ){
    
                if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "/user/person"), HttpStatus.BAD_REQUEST);
                    
                }
                
                String username = jwtProvider.getUsernameFromToken(authorization);
                
                System.out.println("usename: "+ username);
                
                Person person = userService.getByUsername(username).getPerson();
                
                personService.edit(person, personDto);
 
                return new ResponseEntity(new Message("data edited", "/user/person"), HttpStatus.OK);
    }
    
    
    @GetMapping("/api/v1/{username}/person")
    public ResponseEntity<?> getPerson(@PathVariable String username){
    
          try{
        
            Person person = userService.getByUsername(username).getPerson();
            
            return new ResponseEntity(person, HttpStatus.OK);
        
        
        } catch (Exception e) {
        
        
                      return new ResponseEntity("user not found", HttpStatus.BAD_REQUEST);
            
        }
      
          
    }
    
   
    
}
