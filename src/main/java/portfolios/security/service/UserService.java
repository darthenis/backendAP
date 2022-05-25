
package portfolios.security.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import portfolios.email.EmailService;
import portfolios.entity.Person;
import portfolios.security.dto.NewUser;
import portfolios.security.entity.Role;
import portfolios.security.repository.UserRepository;
import portfolios.security.entity.User;

@Service
@Transactional
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
    
    boolean emailExists =  userRepository.existsByEmail(newUser.getEmail());
    
    boolean userExists = userRepository.existsByUsername(newUser.getUsername());
        
    if(emailExists) {
    
        throw new IllegalStateException("email already taken");
    
    }
    
      if(userExists) {
    
        throw new IllegalStateException("user already taken");
    
    }
    
    User user = new User(newUser.getUsername(), newUser.getPassword(), newUser.getEmail());

    Role roles = rolService.findByName("ROLE_USER");
    user.setRoles(Arrays.asList(roles));        
            
    Person person = new Person();
    
    person.setName(newUser.getName());
    person.setLastName(newUser.getLastname());
    
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