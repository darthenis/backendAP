
package portfolios.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EducationDto {
    

    private int id;
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String name;
    
    private String logoUrl;
    
    @NotNull
    private LocalDate initDate;
    
    private LocalDate endDate;
    
    @NotBlank
    private String career;
    
    @NotNull
    private int order;
    
    public EducationDto(){}
}
