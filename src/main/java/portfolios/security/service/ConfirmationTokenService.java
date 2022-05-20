
package portfolios.security.service;

import portfolios.security.repository.ConfirmationTokenRepository;
import portfolios.security.entity.ConfirmationToken;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.security.entity.User;

@Service
public class ConfirmationTokenService {
    
    
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    
    public String createAndSaveConfirmationToken(User user){
        
        String token = UUID.randomUUID().toString();
        
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(120), user);
        
        confirmationTokenRepository.save(confirmationToken);
        
        return token;
    
    }
    
    
    public ConfirmationToken getToken(String token){
    
           return  confirmationTokenRepository.findByToken(token).get();
           
    }
    
    
    public void setConfirmedAt(ConfirmationToken confirmationToken){
        
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        
        confirmationTokenRepository.save(confirmationToken);
    
    }
}
