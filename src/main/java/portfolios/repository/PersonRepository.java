
package portfolios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.entity.Person;
import portfolios.security.entity.User;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{
    
    
    public Person findByUser(User user);
    
}
