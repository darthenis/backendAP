
package portfolios.service;

import java.util.List;
import portfolios.dto.ProjectDto;
import portfolios.entity.Person;
import portfolios.entity.Project;


public interface IProjectService {
    
     public void create(Person person, ProjectDto projectDto);
      
      public boolean edit(Person person, List<ProjectDto> projectsDto);
      
      public boolean delete(Person person, int id);

      public List<Project> getProjects(String username);
      
}
