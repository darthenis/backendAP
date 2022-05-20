
package portfolios.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.entity.Experience;
import portfolios.entity.Person;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer>{
    
     public List<Experience> findByPerson(Person person);
     
     public Experience findById(int id);
    
}
