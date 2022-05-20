
package portfolios.service;


import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.dto.PrivateMessagesDto;
import portfolios.entity.Person;
import portfolios.entity.PrivateMessages;
import portfolios.repository.PrivateMessagesRepository;


@Service
public class PrivateMessagesService implements IPrivateMessagesService {
    
    @Autowired
    PrivateMessagesRepository privateMessagesRepository;

    @Override
    public void create(Person person, PrivateMessagesDto privateMessagesDto) throws Exception {
        
        try{
            
            PrivateMessages privateMessage = new PrivateMessages();
            privateMessage.setName(privateMessagesDto.getName());
            privateMessage.setEmail(privateMessagesDto.getEmail());
            privateMessage.setMessage(privateMessagesDto.getMessage());
            privateMessage.setCreatedAt(LocalDateTime.now());
            privateMessage.setPerson(person);
            privateMessage.setSeen(false);
            
            privateMessagesRepository.save(privateMessage);
            
        } catch(Exception e){
        
            e.printStackTrace();
                    
        }
        
        
    }

    @Override
    public boolean editSeen(Person person, PrivateMessagesDto privateMessagesDto) throws Exception {
        
        PrivateMessages privateMessages = privateMessagesRepository.getById(privateMessagesDto.getId());
        
        if(privateMessages != null){
        
            boolean authorized = privateMessages.getPerson().getId() == person.getId();
            
            if(authorized){
            
                privateMessages.setSeen(privateMessagesDto.getSeen());
                privateMessagesRepository.save(privateMessages);
                
                return true;
            
            }
            
            return false;
            
        }
        
        return false;
        
        
    }

    @Override
    public boolean delete(Person person, long id) {
        
        PrivateMessages privateMessages = privateMessagesRepository.findById(id).get();
        
        if(privateMessages != null){
        
            boolean authorized = privateMessages.getPerson().getId() == person.getId();
            
            if(authorized){
            
                privateMessagesRepository.deleteById(id);
                
                return true;
            
            }
            
            return false;
        
        }
        
        return false;
    }

    @Override
    public List<PrivateMessages> findByPerson(Person person) {
        return privateMessagesRepository.findByPerson(person);
    }
    
}
