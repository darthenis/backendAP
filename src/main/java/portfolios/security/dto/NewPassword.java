
package portfolios.security.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPassword {
    
    @NotNull
    @Digits(integer=6, fraction=0)
    private int code;
    
    @NotBlank
    @Size(max = 16)
    private String password;
    
    public NewPassword(){}
    
}
