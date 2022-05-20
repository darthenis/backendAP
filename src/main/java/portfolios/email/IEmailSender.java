
package portfolios.email;


public interface IEmailSender {
    
        
        void send(String to, String username, String token);
        
        void sendCode(String to, String username, String code);
    
}
