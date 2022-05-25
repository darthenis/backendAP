
package portfolios.security.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import portfolios.security.recaptcha.CaptchaSettings;
import portfolios.security.recaptcha.ReCaptchaResponse;



@Service
public class CaptchaService implements ICaptchaService {
    
    @Autowired
    private CaptchaSettings captchaSettings;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public ReCaptchaResponse verify(String response) {
        
        
        URI verifyURI = URI.create(
                    String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s", captchaSettings.getSecret(), response));
        
        ReCaptchaResponse reCaptchaResponse = restTemplate.getForObject(verifyURI, ReCaptchaResponse.class);

        if(reCaptchaResponse != null){
                
            if(reCaptchaResponse.isSuccess() && (reCaptchaResponse.getScore() < captchaSettings.getThreshold()
              || !reCaptchaResponse.getAction().equals("contact"))){
            
                  reCaptchaResponse.setSuccess(false);
            
            }
                
        }
        
        return reCaptchaResponse;
        
    }


}
