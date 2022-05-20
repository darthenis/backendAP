
package portfolios.service;

import java.util.List;
import portfolios.dto.ExperienceDto;
import portfolios.entity.Experience;
import portfolios.entity.Person;


public interface IExperienceService {
    
    
      public boolean edit(Person person, List<ExperienceDto> experienceDto) throws Exception;
      
      public void create(Person person, ExperienceDto experienceDto) throws Exception;
      
      public boolean delete(Person person, int id);

      public List<Experience> getExperiences(String username);
    
}
