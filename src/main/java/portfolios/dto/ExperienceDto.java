
package portfolios.dto;

import java.time.LocalDate;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ExperienceDto {
        
        private int id;
        
        @NotBlank
        private String title;
    
        @NotBlank
        private String name;
        
        private String logoUrl;
        
        @NotBlank
        private String job;
        
        @NotNull
        private LocalDate dateInit;
        
        private LocalDate dateEnd;
        
        private String info;
        
        @NotNull
        private int order;
        
        
        public ExperienceDto(){}
    
}
