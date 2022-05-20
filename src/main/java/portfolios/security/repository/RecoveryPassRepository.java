
package portfolios.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import portfolios.security.entity.RecoveryPass;

@Repository
public interface RecoveryPassRepository extends JpaRepository<RecoveryPass, Integer>{
    
    RecoveryPass findByToken(int token);
    
}
