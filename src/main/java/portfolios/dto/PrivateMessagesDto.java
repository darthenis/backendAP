
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
    
   
    @NotBlank
    @Size(max=30)
    private String name;
    
    @Email
    @Size(max=30)
    @NotNull
    private String email;
    
    @Size(max=255)
    @NotBlank
    private String message;
    
    @NotNull
    private boolean seen;
    
    public PrivateMessagesDto(){}
    
    public boolean getSeen(){
    
        return this.seen;
    
    }
    
    
}
