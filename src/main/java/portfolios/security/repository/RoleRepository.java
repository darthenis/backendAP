
package portfolios.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.security.entity.Role;
import portfolios.security.enums.RolName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    
        Role findByName(String name);
        
}
