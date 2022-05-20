
package portfolios.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.entity.Person;
import portfolios.entity.PrivateMessages;

@Repository
public interface PrivateMessagesRepository extends JpaRepository<PrivateMessages, Long>{
    
        public List<PrivateMessages> findByPerson(Person person);
    
    
}
