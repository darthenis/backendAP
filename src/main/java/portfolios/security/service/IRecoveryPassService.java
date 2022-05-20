
package portfolios.security.service;

import portfolios.security.entity.RecoveryPass;
import portfolios.security.entity.User;


public interface IRecoveryPassService {
    
     public RecoveryPass createAndSaveCode(User user);
     
     public RecoveryPass getCode(int code);
     
     public void setConfirmedAt(RecoveryPass recoveryPass);
     
     public String confirmCode(int code);
    
}
