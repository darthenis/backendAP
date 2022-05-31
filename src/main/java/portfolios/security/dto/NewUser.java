
package portfolios.security.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NewUser {
    
        @NotBlank
        @Size(max = 10)
        private String username;
        
        @NotBlank
        @Size(max = 16)
        private String password;
        
        @Email
        @NotBlank
        @Size(max = 30)
        private String email;
        
        @NotBlank
        @Size(min = 3, max = 13)
        private String name;
        
        @NotBlank
        @Size(min = 3, max = 13)
        private String lastname;
        
        private Set<String> roles = new HashSet<>();
        
        public NewUser(){}
}
