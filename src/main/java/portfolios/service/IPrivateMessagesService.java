
package portfolios.service;

import java.util.List;
import portfolios.dto.PrivateMessagesDto;
import portfolios.entity.Person;
import portfolios.entity.PrivateMessages;


public interface IPrivateMessagesService {
    
    
    public void create(Person person, PrivateMessagesDto privateMessagesDto) throws Exception;
    
    public boolean editSeen(Person person, PrivateMessagesDto privateMessagesDto) throws Exception;
    
    public boolean delete(Person person, long id);

    public List<PrivateMessages> findByPerson(Person person);
    
}
