
package portfolios.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class LoginResponse {
    
    private String token;
    
    private int id;

    public LoginResponse(String token, int id) {
        this.token = token;
        this.id = id;
    }
    
    
}
