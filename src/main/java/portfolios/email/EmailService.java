
package portfolios.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailSender {
    
    
    private final static String LINK = "http://localhost:4200/auth/confirm/";
    
    @Autowired
    private JavaMailSender mailSender;
    
    private final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    
    @Override
    @Async
    public void send(String to, String username, String token){
        
       try {
       
           MimeMessage mimeMessage = mailSender.createMimeMessage();
           
           MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
           
           String link = LINK + token;
           
           helper.setText(this.buildEmail(username, link), true);
           
           helper.setTo(to);
           
           helper.setSubject("confirme su email");
           
           helper.setFrom("emi.acevedo.letras@gmail.com");
           
           mailSender.send(mimeMessage);
       
       } catch (MessagingException e){
       
           
           LOGGER.error("failed to send email", e.getMessage());
           
           throw new IllegalStateException("failed to send email");
           
       }
    }
    
    public void sendCode(String to, String username, String code){
    
        try{
            
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            
            helper.setText(this.buildEmailCode(username, code), true);

            helper.setTo(to);

            helper.setSubject("Recuperación de contraseña");

            helper.setFrom("emi.acevedo.letras@gmail.com");

            mailSender.send(mimeMessage);
        
        } catch(MessagingException e){
        
                    LOGGER.error("failed to send email", e.getMessage());
           
                    throw new IllegalStateException("failed to send email");
        
        }
    
    }
    
    public String buildEmailCode(String username, String code){
    
        return "<h1>Codigo de recuperación de contraseña</h1>"+
                "<p>Tu codigo de recuperación de password es:</p>"+
                "<h3>"+ code +"</h3>";
        
    }
    
    
    public String buildEmail(String username, String link){
    
            return "<h1>Bienvenid@ a Apportfolios"+ username +"</h1>"+
                    "<p>Active su cuenta desde el siguiente link:</p>"+ link;
            
    }
            
}
