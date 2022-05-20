
package portfolios.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.entity.Education;
import portfolios.entity.Person;

@Repository
public interface EducationRepository extends JpaRepository<Education, Integer>{
    

    public List<Education> findByPerson(Person person);
    
    public Education findById(int id);
    
    
}
