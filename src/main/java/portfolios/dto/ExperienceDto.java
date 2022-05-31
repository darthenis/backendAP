
package portfolios.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExperienceDto {
        
        private int id;
        
        @NotBlank
        @Size(min = 4, max = 25)
        private String title;
    
        @NotBlank
        @Size(min = 4, max = 25)
        private String name;
        
        private String logoUrl;
        
        @NotBlank
        @Size(min = 4, max = 25)
        private String job;
        
        @NotNull
        private LocalDate dateInit;
        
        private LocalDate dateEnd;
        
        @Size(max = 255)
        private String info;
        
        @NotNull
        private int order;
        
        
        public ExperienceDto(){}
    
}
