
package portfolios.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.security.entity.User;

@Repository 
public interface UserRepository extends JpaRepository<User, Integer> {
    
        User findByUsername(String username);
        Boolean existsByUsername(String username);
        Boolean existsByEmail(String email);
        User findByEmail(String email);
}
