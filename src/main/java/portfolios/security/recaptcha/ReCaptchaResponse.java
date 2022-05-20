
package portfolios.security.recaptcha;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "success",
    "score",
    "action",
    "challenge_ts",
    "hostname",
    "error-codes"
})
public class ReCaptchaResponse {
    
    
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("challenge_ts")
    private String challengeTs;
    
    @JsonProperty("hostname")
    private String hostname;
    
    @JsonProperty("error-codes")
    private List<String> errorCodes;
    
    @JsonProperty("score")
    private float score;
    
    @JsonProperty("action")
    private String action;
    
    public List<String> getErrors(){
    
        if(getErrorCodes() != null){
        
                return getErrorCodes().stream()
                        .map(ReCaptchaErrorCodes.RECAPTCHA_ERROR_CODES::get)
                        .collect(Collectors.toList());
        }
 
        return null;
      
    }
}
