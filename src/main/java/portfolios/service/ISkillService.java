
package portfolios.service;

import java.util.List;
import portfolios.dto.SkillDto;
import portfolios.entity.Person;
import portfolios.entity.Skill;


public interface ISkillService {
    
      public void create(Person person, SkillDto skillDto);
      
      public boolean edit(Person person, List<SkillDto> skillDto);
      
      public boolean delete(Person person, int id);

      public List<Skill> getSkills(String username);
}
