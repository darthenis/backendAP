
package portfolios.service;

import java.util.List;
import portfolios.dto.EducationDto;
import portfolios.entity.Education;
import portfolios.entity.Person;


public interface IEducationService {
    
    
      public boolean edit(Person person, List<EducationDto> educationDto);
      
      public boolean delete(Person person, int eduId);

      public List<Education> getEducations(String username);
      
      public void create(Person person, EducationDto educationDto) throws Exception;
      
      
      
}
