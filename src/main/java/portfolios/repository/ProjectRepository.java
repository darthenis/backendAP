
package portfolios.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.entity.Person;
import portfolios.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    
     public List<Project> findByPerson(Person person);
    
}
