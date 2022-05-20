
package portfolios.security.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portfolios.security.entity.Privilege;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>{
    
    public Privilege findByName(String name);
    
}
