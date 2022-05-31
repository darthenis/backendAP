
package portfolios.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SkillDto {
    
    private int id;
    
    @NotBlank
    @Size(max = 10)
    private String name;
    
    @NotNull
    private int porcent;
    
    @NotNull
    private int order;
    
    public SkillDto(){}
}
