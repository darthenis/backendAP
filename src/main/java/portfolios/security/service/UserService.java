
package portfolios.security.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import portfolios.email.EmailService;
import portfolios.entity.Person;
import portfolios.security.dto.NewUser;
import portfolios.security.entity.Role;
import portfolios.security.repository.UserRepository;
import portfolios.security.entity.User;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleService rolService;
    
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    EmailService emailService;
   
    
    
    public User getByUsername(String username){
    
        return userRepository.findByUsername(username);
    
    }
    
    public User getByEmail(String email){
    
        return userRepository.findByEmail(email);
    
    }
    
    public boolean existsByUsername(String username){
    
        return userRepository.existsByUsername(username);
    
    }
    
    public boolean existsByEmail(String email){
    
        return userRepository.existsByEmail(email);
    }
    
    public void save(User user){
    
        userRepository.save(user);
    
    }
    
    
    
    
    public void signUpUser(NewUser newUser){
    
    User user = new User(newUser.getUsername(), newUser.getPassword(), newUser.getEmail());

    Role roles = rolService.findByName("ROLE_USER");
    user.setRoles(Arrays.asList(roles));        
            
    Person person = new Person();
    
    person.setName(newUser.getName());
    person.setLastName(newUser.getLastname());
    person.setCountry(newUser.getCountry());
    person.setState(newUser.getState());
    person.setCity(newUser.getCity());
    
    user.setPerson(person);
            
    user.getPerson().setUser(user); //set relations
            
    userRepository.save(user); //save in db

    String token = confirmationTokenService.createAndSaveConfirmationToken(user);
    
    emailService.send(user.getEmail(), user.getUsername(), token);
    
    }
    
    
    public void enableUser(String username){
        
          User user = userRepository.findByUsername(username);

          user.setEnabled(true);
          
          userRepository.save(user);
    
    }
    
    public void editUser(User user){
    
        userRepository.save(user);
  
    }
}
