
package portfolios.security.repository;

import portfolios.security.entity.ConfirmationToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer>{
    
    Optional <ConfirmationToken> findByToken(String token);
    
}
