
package portfolios.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PersonDto {
    
    private int id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    private String country;
    
    @NotBlank
    private String state;
    
    @NotBlank
    private String city;
    
    private String profile;
    
    private String banner;
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String info;
    
    private String facebook;
    
    private String twitter;
    
    private String instagram;
    
    private String linkedin;
    
    private String github;
    
    
    public PersonDto(){}
}
