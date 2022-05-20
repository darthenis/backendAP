
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
import portfolios.dto.Message;
import portfolios.dto.SkillDto;
import portfolios.entity.Person;
import portfolios.entity.Skill;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.service.UserService;
import portfolios.service.SkillService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SkillController {
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    SkillService skillService;

    @Autowired
    UserService userService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/user/skill")
    public ResponseEntity<?> createSkill( @RequestHeader("Authorization") String authorization,
                                                             @Valid @RequestBody  SkillDto skillDto, BindingResult bindingResult,
                                                              HttpServletResponse res
                                                           ){
    
                if(bindingResult.hasErrors()){
                    
                    res.setStatus(400);
                    return new ResponseEntity(new Message("Error in fields", "/user/skill"), HttpStatus.BAD_REQUEST);
                    
                }
                
                String username = jwtProvider.getUsernameFromToken(authorization);
                
                Person person = userService.getByUsername(username).getPerson();
                
                skillService.create(person, skillDto);
                
                List<Skill> skills = skillService.getSkills(username);
       
                 res.setStatus(201);
                 return new ResponseEntity(skills, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/api/v1/user/skill")
    public ResponseEntity<?> editSkill(@RequestHeader("Authorization") String authorization,
                                                                                    @Valid @RequestBody List<SkillDto> skillDto, BindingResult bindingResult){
    
           if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "/user/skill"), HttpStatus.BAD_REQUEST);

               }
           
             String username = jwtProvider.getUsernameFromToken(authorization);
             
             Person person = userService.getByUsername(username).getPerson();
                
            if(skillService.edit(person, skillDto)){
            
                    return new ResponseEntity("skill saved", HttpStatus.OK);
            
            } else {
            
                
                    return new ResponseEntity("id error", HttpStatus.BAD_REQUEST);
            
            }
       
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/api/v1/user/skill/{id}")
    public Message deleteSkill(@RequestHeader("Authorization") String authorization,
                               @PathVariable("id") int id,
                               HttpServletResponse res){

         String username = jwtProvider.getUsernameFromToken(authorization);
         
         Person person = userService.getByUsername(username).getPerson();
         
         if(skillService.delete(person, id)){
         
         
                    res.setStatus(200);
                    return new Message("Skill ", "/user/skill");
         
         }
    
                    res.setStatus(400);
                    return new Message("id not found", "/user/skill");
         
    }
    
    @GetMapping("/api/v1/{username}/skill")
    public ResponseEntity<?> getSkill(@PathVariable String username){
    
              try{
        
            List<Skill> skills = skillService.getSkills(username);
            
            return new ResponseEntity(skills, HttpStatus.OK);
        
        
        } catch (Exception e) {
        
        
                      return new ResponseEntity("user not found", HttpStatus.BAD_REQUEST);
            
        }
    }
    
    
}
