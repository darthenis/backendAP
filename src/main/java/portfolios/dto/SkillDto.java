
package portfolios.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SkillDto {
    
    private int id;
    
    @NotBlank
    private String name;
    
    @NotNull
    private int porcent;
    
    @NotNull
    private int order;
    
    public SkillDto(){}
}
