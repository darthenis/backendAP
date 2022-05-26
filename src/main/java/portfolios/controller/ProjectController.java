
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
import portfolios.dto.ProjectDto;
import portfolios.entity.Person;
import portfolios.entity.Project;
import portfolios.security.jwt.JwtProvider;
import portfolios.security.service.UserService;
import portfolios.service.ProjectService;

@RestController
public class ProjectController {
    
    @Autowired
    JwtProvider jwtProvider;
    
    @Autowired
    ProjectService projectService;
    
    @Autowired
    UserService userService;
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/user/project")
    public ResponseEntity<?> createProject( @RequestHeader("Authorization") String authorization,
                                            @Valid @RequestBody  ProjectDto projectDto, BindingResult bindingResult
                                                           ){
    
                if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "/user/project"), HttpStatus.BAD_REQUEST);
                    
                }
                
                String username = jwtProvider.getUsernameFromToken(authorization);
                
                Person person = userService.getByUsername(username).getPerson();
                
                projectService.create(person, projectDto);
                
                List<Project> projects = projectService.getProjects(username);
       
                return new ResponseEntity(projects, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/api/v1/user/project")
    public ResponseEntity<?> editProject(@RequestHeader("Authorization") String authorization,
                                                                                    @Valid @RequestBody List<ProjectDto> projectsDto, BindingResult bindingResult){
    
           if(bindingResult.hasErrors()){
                
                    return new ResponseEntity(new Message("Error in fields", "/user/project"), HttpStatus.BAD_REQUEST);

               }
           
             String username = jwtProvider.getUsernameFromToken(authorization);
             
             Person person = userService.getByUsername(username).getPerson();
                
            if(projectService.edit(person, projectsDto)){
            
                    return new ResponseEntity(new Message("project saved", "/user/project"), HttpStatus.OK);
            
            } else {
            
                
                    return new ResponseEntity(new Message("id error", "/user/project"), HttpStatus.BAD_REQUEST);
            
            }

    }
            
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/api/v1/user/project/{id}")
    public Message deleteProject(@RequestHeader("Authorization") String authorization,
                                           @PathVariable("id") int id, HttpServletResponse res){
 
         
         String username = jwtProvider.getUsernameFromToken(authorization);
         
         Person person = userService.getByUsername(username).getPerson();
         
         if(projectService.delete(person, id)){
         
              res.setStatus(200);
              return new Message("Project deleted", "/user/project");
         
         }
    
            res.setStatus(400);
            return new Message("id not found", "/user/project");
         
    }
        
    @GetMapping("/api/v1/{username}/project")
    public ResponseEntity<?> getProjects(@PathVariable String username){
    
            try{
  
                List<Project> projects = projectService.getProjects(username);

                return new ResponseEntity(projects, HttpStatus.OK);

            } catch (Exception e) {
                
                    e.printStackTrace();

                    return new ResponseEntity(new Message("user not found", "username/project"), HttpStatus.BAD_REQUEST);
            
            }
    }
    
}
