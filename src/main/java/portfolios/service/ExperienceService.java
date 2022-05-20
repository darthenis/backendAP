
package portfolios.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.dto.ExperienceDto;
import portfolios.entity.Experience;
import portfolios.entity.Person;
import portfolios.repository.ExperienceRepository;
import portfolios.security.repository.UserRepository;

@Service
public class ExperienceService implements IExperienceService{

    @Autowired
    ExperienceRepository experienceRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public void create(Person person, ExperienceDto experienceDto)throws Exception{
    
        try{
    
            Experience experience = new Experience();
                experience.setTitle(experienceDto.getTitle());
                experience.setName(experienceDto.getName());
                experience.setJob(experienceDto.getJob());
                experience.setDateInit(experienceDto.getDateInit());
                experience.setDateEnd(experienceDto.getDateEnd());
                experience.setLogoUrl(experienceDto.getLogoUrl());
                experience.setInfo(experienceDto.getInfo());
                experience.setPerson(person);
        
                experienceRepository.save(experience);
                
                
        }catch(Exception e){
        
       
             throw new Exception(e);
        
        
        }
        
   
    }
    
    @Override
    public boolean edit(Person person ,List<ExperienceDto> experienceDto) throws Exception{
          
        List<Experience> experiences = new ArrayList<>();
        
        for(ExperienceDto e : experienceDto){
        
          try{
                 
                Experience experience = experienceRepository.findById(e.getId());
                        
                if(person.getId() == experience.getPerson().getId()){
            
                experience.setTitle(e.getTitle());
                experience.setName(e.getName());
                experience.setJob(e.getJob());
                experience.setDateInit(e.getDateInit());
                experience.setDateEnd(e.getDateEnd());
                experience.setLogoUrl(e.getLogoUrl());
                experience.setInfo(e.getInfo());
                experience.setOrder(e.getOrder());
                experience.setPerson(person);
        
                experiences.add(experience);
                          
               } else {
        
                return false;
        
                }
                
          } catch (Exception error){
              
                 error.printStackTrace();
                 throw new Exception(error.getMessage());
   
         }   
        
    
        
        }
        
        
      experienceRepository.saveAll(experiences);
       
      return true;
    }

    @Override
    public boolean delete(Person person, int id) {
   
          try {
              
                    Experience experience = experienceRepository.findById(id);
          
                    if(person.getId() == experience.getPerson().getId()){
                    
                        experienceRepository.delete(experience);
                                            
                        return true;
                    
                    }
                    
                    return false;
              
              
              } catch (Exception e){
              
                    e.printStackTrace();
                    
                    return false;
 
              }
    }

    @Override
    public List<Experience> getExperiences(String username) {
        
        Person person = userRepository.findByUsername(username).getPerson();
        
       return experienceRepository.findByPerson(person);
    }
    
}
