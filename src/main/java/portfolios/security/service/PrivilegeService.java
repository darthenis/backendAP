
package portfolios.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolios.security.entity.Privilege;
import portfolios.security.repository.PrivilegeRepository;


@Service
@Transactional
public class PrivilegeService {
    
    @Autowired
    PrivilegeRepository privilegeRepository;
    
    public Privilege findByName(String name){
    
        return privilegeRepository.findByName(name);
    
    }
    
    public void save(Privilege privilege){
    
            privilegeRepository.save(privilege);
    
    }
        
    
}
