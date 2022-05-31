
package portfolios.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonDto {
    
    private int id;
    
    @NotBlank
    @Size(min = 3, max = 13)
    private String name;
    
    @NotBlank
    @Size(min = 3, max = 13)
    private String lastName;
    
    @NotBlank
    @Size(min = 4, max = 15)
    private String country;
    
    @NotBlank
    @Size(min = 4, max = 15)
    private String state;
    
    @NotBlank
    @Size(min = 4, max = 15)
    private String city;
    
    @Size(max = 255)
    private String profile;
    
    @Size(max = 255)
    private String banner;
    
    @NotBlank
    @Size(min = 4, max = 30)
    private String title;
    
    @NotBlank
    @Size(min = 4, max = 250)
    private String info;
    
    @Size(max = 250)
    private String facebook;
    
    @Size(max = 250)
    private String twitter;
    
    @Size(max = 250)
    private String instagram;
    
    @Size(max = 250)
    private String linkedin;
    
    @Size(max = 250)
    private String github;
    
    
    public PersonDto(){}
}
