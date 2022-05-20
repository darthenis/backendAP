
package portfolios.security.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUser {
    
        @NotBlank
        private String username;
        
        @NotBlank
        private String password;
        
        @Email
        @NotBlank
        private String email;
        
        @NotBlank
        private String name;
        
        @NotBlank
        private String lastname;
        
        private Set<String> roles = new HashSet<>();
        
        public NewUser(){}
}
