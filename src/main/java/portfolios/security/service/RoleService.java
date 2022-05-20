
package portfolios.security.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolios.security.entity.Role;
import portfolios.security.enums.RolName;
import portfolios.security.repository.RoleRepository;


@Service
@Transactional
public class RoleService {
    
    @Autowired
    RoleRepository roleRepository;
    
    public Role findByName(String name){
    
        return roleRepository.findByName(name);
    
    }
    
    public void save(Role rol){
    
            roleRepository.save(rol);
    
    }
        
    
}
