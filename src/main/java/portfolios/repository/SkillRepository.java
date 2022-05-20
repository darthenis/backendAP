
package portfolios.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.entity.Person;
import portfolios.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer>{
    
     public List<Skill> findByPerson(Person person);
    
}
