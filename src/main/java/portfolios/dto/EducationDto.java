
package portfolios.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EducationDto {
    

    private int id;
    
    @NotBlank
    @Size(min = 4, max = 25)
    private String title;
    
    @NotBlank
    @Size(min = 4, max = 25)
    private String name;
    
    @Size(max = 255)
    private String logoUrl;
    
    @NotNull
    private LocalDate initDate;
    
    private LocalDate endDate;
    
    @NotBlank
    @Size(min = 4, max = 25)
    private String career;
    
    @NotNull
    private int order;
    
    public EducationDto(){}
}
