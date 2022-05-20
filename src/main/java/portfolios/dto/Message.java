
package portfolios.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Message {
    
        private String message;
        private String path;
        
    public Message(){}

    public Message(String message, String path) {
        this.message = message;
        this.path = path;
    }
    


    
}
