
package portfolios.security.service;

import portfolios.security.recaptcha.ReCaptchaResponse;


public interface ICaptchaService {
    
    public ReCaptchaResponse verify(String response);
    
}
