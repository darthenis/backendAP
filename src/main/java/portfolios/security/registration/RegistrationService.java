
package portfolios.security.registration;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.security.entity.ConfirmationToken;
import portfolios.security.service.ConfirmationTokenService;
import portfolios.security.service.UserService;

@Service
public class RegistrationService {
    
    @Autowired
    EmailValidator emailValidator;
    
    @Autowired
    UserService userService;
    
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    

    public String confirmToken(String token) throws IllegalStateException{
    
        
        try{
            
                    ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);
                    
                      if(confirmationToken.getConfirmedAt() != null){
                      
                      
                            throw new IllegalStateException("email already confirmed");
                 
                      }
                      
                      
                      LocalDateTime expiresAt = confirmationToken.getExpiresAt();
                      
                      if(expiresAt.isBefore(LocalDateTime.now())){
                      
                          throw new IllegalStateException("token expired");
                      
                      }
                      
                      
                      confirmationTokenService.setConfirmedAt(confirmationToken);
                      
                      userService.enableUser(confirmationToken.getUser().getUsername());
                      
                      return "Confirmed";
        
                   
                } catch (IllegalStateException e){
                
                   throw new IllegalStateException("token not found", e);
                
                }
        
              
    
    }
    
    
    
}
