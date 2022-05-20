
package portfolios.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import portfolios.security.entity.Role;
import portfolios.security.enums.RolName;
import portfolios.security.service.RoleService;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RoleService rolService;
    
    @Override
    public void run(String... args) throws Exception {
            
           /* Rol rolAdmin = new Rol(RolName.ROLE_ADMIN);
           
            rolService.save(rolAdmin);*/
            
            
            
    }
    
    
    
}
