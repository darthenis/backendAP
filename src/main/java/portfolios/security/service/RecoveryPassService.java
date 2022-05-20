
package portfolios.security.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.security.entity.RecoveryPass;
import portfolios.security.entity.User;
import portfolios.security.repository.RecoveryPassRepository;

@Service
public class RecoveryPassService implements IRecoveryPassService {

    @Autowired
    RecoveryPassRepository recoveryPassRepository;
    
    @Autowired
    UserService userService;
    
    @Override
    public RecoveryPass createAndSaveCode(User user) {
        
        boolean done = false;
        
        int code;
        
        RecoveryPass recoveryPass = new RecoveryPass();
        
        do{
        
            Random rnd = new Random();
        
            int newCode = 0;
            
            do{
            
                  newCode = rnd.nextInt(999999);
            
            }while(String.valueOf(newCode).length() != 6);
            
            System.out.println(newCode);
            
            RecoveryPass recoveryP = getCode(newCode);
         
            if(recoveryP == null){
                
                code = newCode;

                return create(recoveryPass, user, code);

   
            } else {
                
                boolean isExpired = recoveryP.getExpiresAt().isBefore(LocalDateTime.now());
            
                if(isExpired){

                    recoveryPassRepository.delete(recoveryP);

                    code = newCode;
                    
                   return  create(recoveryPass, user, code);
     
                }
            
            
            }
            
        }while(!done);
        
        return recoveryPass;
                 
    }

    @Override
    public RecoveryPass getCode(int code) {
        
       return recoveryPassRepository.findByToken(code);
       
    }

    @Override
    public void setConfirmedAt(RecoveryPass recoveryPass) {
       
        recoveryPass.setConfirmedAt(LocalDateTime.now());
        
        recoveryPassRepository.save(recoveryPass);
        
    }

    @Override
    public String confirmCode(int code) throws IllegalStateException{
        try{
            
                    RecoveryPass recoveryPass = getCode(code);
                    
                      if(recoveryPass.getConfirmedAt() != null){
                      
                            throw new IllegalStateException("email already confirmed");
                 
                      }
                      
                      
                      LocalDateTime expiresAt = recoveryPass.getExpiresAt();
                      
                      if(expiresAt.isBefore(LocalDateTime.now())){
                      
                          throw new IllegalStateException("token expired");
                      
                      }
                      
                      
                      recoveryPass.setConfirmedAt(LocalDateTime.now());
                      
                      userService.enableUser(recoveryPass.getUser().getUsername());
                      
                      return "Confirmed";
        
                   
                } catch (IllegalStateException e){
                
                   throw new IllegalStateException("token not found", e);
                
                }
    }
    
    public void deleteCode(RecoveryPass recoveryPass){
    
        recoveryPassRepository.delete(recoveryPass);
    }
    
    public RecoveryPass create(RecoveryPass recoveryPass, User user, int code){
    
               
                 recoveryPass.setToken(code);
                recoveryPass.setCreatedAt(LocalDateTime.now());
                recoveryPass.setExpiresAt(LocalDateTime.now().plusMinutes(120));
                recoveryPass.setUser(user);
    
                return recoveryPass;

    }

}
