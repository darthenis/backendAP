
package portfolios.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;



@Getter @Setter
public class ProjectDto {
    
    private int id;
    
    @NotBlank
    private String title;
    
    private String img;
    
    @NotNull
    private LocalDate date;
    
    @NotBlank
    private String info;
    
    private String url;
    
    @NotNull
    private int order;
    
    public ProjectDto(){}
}
