
package portfolios.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter @Setter
public class ProjectDto {
    
    private int id;
    
    @NotBlank
    @Size(min = 4, max = 20)
    private String title;
    
    @Size(max = 255)
    private String picUrl;
    
    @NotNull
    private LocalDate atCreated;
    
    @NotBlank
    @Size(max = 255)
    private String info;
    
    @Size(max = 255)
    private String url;
    
    @NotNull
    private int order;
    
    public ProjectDto(){}
}
