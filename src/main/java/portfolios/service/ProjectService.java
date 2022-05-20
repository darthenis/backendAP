
package portfolios.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.dto.ProjectDto;
import portfolios.entity.Person;
import portfolios.entity.Project;
import portfolios.repository.ProjectRepository;
import portfolios.security.repository.UserRepository;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public void create(Person person, ProjectDto projectDto) {

     Project project = new Project();
                project.setTitle(projectDto.getTitle());
                project.setDate(projectDto.getDate());
                project.setInfo(projectDto.getInfo());
                project.setUrl(projectDto.getUrl());
                project.setPicUrl(projectDto.getImg());
                project.setOrder(projectDto.getOrder());
                project.setPerson(person);
        
                projectRepository.save(project);
    }
    
    @Override 
    public boolean edit(Person person, List<ProjectDto> projectsDto){
        
        List<Project> projects = new ArrayList<>();
        
        int idPerson = person.getId();
        
        for(ProjectDto e : projectsDto){
        
           try{

                Project project = projectRepository.findById(e.getId()).get();
                
                if(idPerson == project.getPerson().getId()){
                
                project.setTitle(e.getTitle());
                project.setDate(e.getDate());
                project.setInfo(e.getInfo());
                project.setUrl(e.getUrl());
                project.setPicUrl(e.getImg());
                project.setOrder(e.getOrder());
                project.setPerson(person);
                
                projects.add(project);

               } else {
        
                return false;
        
                }
                
          } catch (Exception error){  
                 error.printStackTrace();
                 return false;
   
         }   
        
        }
        
          projectRepository.saveAll(projects);
                
          return true;
    
    }

    @Override
    public boolean delete(Person person, int id) {
        
          try {
              
                    Project project = projectRepository.findById(id).get();
          
                    if(person.getId() == project.getPerson().getId()){
                    
                    
                        projectRepository.delete(project);
                        
                        
                        return true;
                    
                    }
                    
                    return false;
              
              
              } catch (Exception e){
              
                    e.printStackTrace();
                    
                    return false;
 
              }
    }

    @Override
    public List<Project> getProjects(String username) {
        
        Person person = userRepository.findByUsername(username).getPerson();
        
        return projectRepository.findByPerson(person);
    }
    
}
