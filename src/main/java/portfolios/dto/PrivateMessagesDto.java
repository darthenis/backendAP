
package portfolios.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PrivateMessagesDto {
    
    
    private long id;
    

    private String username;
    
    @Size(max=50)
    @NotBlank
    private String name;
    
    @Email
    @NotNull
    private String email;
    
    @Size(max=1600)
    @NotBlank
    private String message;
    
    @NotNull
    private boolean seen;
    
    public PrivateMessagesDto(){}
    
    public boolean getSeen(){
    
        return this.seen;
    
    }
    
    
}
