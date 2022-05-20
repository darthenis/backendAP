
package portfolios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.dto.PersonDto;
import portfolios.entity.Person;
import portfolios.repository.PersonRepository;

@Service
public class PersonService implements IPersonService {
    
    @Autowired
    PersonRepository personRepository;

    
    @Override
    public void edit(Person person, PersonDto personDto) {
       
                person.setName(personDto.getName());
                person.setLastName(personDto.getLastName());
                person.setCountry(personDto.getCountry());
                person.setState(personDto.getState());
                person.setCity(personDto.getCity());
                person.setProfile(personDto.getProfile());
                person.setTitle(personDto.getTitle());
                person.setInfo(personDto.getInfo());
                person.setBanner(personDto.getBanner());
                person.setFacebook(personDto.getFacebook());
                person.setTwitter(personDto.getTwitter());
                person.setLinkedin(personDto.getLinkedin());
                person.setInstagram(personDto.getInstagram());
                person.setGithub(personDto.getGithub());
               
        personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

    @Override
    public void createPerson(Person person) {
        personRepository.save(person);
    }
    
    
        
    
}
