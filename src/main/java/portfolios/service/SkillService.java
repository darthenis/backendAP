
package portfolios.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.dto.SkillDto;
import portfolios.entity.Person;
import portfolios.entity.Skill;
import portfolios.repository.SkillRepository;
import portfolios.security.repository.UserRepository;

@Service
public class SkillService implements ISkillService {

    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public void create(Person person, SkillDto skillDto) {

       Skill skill = new Skill();
                skill.setName(skillDto.getName());
                skill.setPorcent(skillDto.getPorcent());
                skill.setOrder(skillDto.getOrder());
                skill.setPerson(person);
              
                skillRepository.save(skill);
    }
    
    @Override
    public boolean edit(Person person, List<SkillDto> skillsDto){
        
        List<Skill> skills = new ArrayList<>();
        
        for(SkillDto e : skillsDto)
    
        try{

                Skill skill = skillRepository.findById(e.getId()).get();
                
                if(person.getId() == skill.getPerson().getId()){
                
                skill.setName(e.getName());
                skill.setPorcent(e.getPorcent());
                skill.setOrder(e.getOrder());
                skill.setPerson(person);
                
               } else {
        
                return false;
        
                }
                
          } catch (Exception error){  
                 error.printStackTrace();
                 return false;
   
         }   
        
           skillRepository.saveAll(skills);
                
           return true;
    
    }

    @Override
    public boolean delete(Person person, int id) {
        
          try {
              
                    Skill skill = skillRepository.findById(id).get();
          
                    if(person.getId() == skill.getPerson().getId()){
                    
                    
                        skillRepository.delete(skill);
                        
                        
                        return true;
                    
                    }
                    
                    return false;
              
              
              } catch (Exception e){
              
                    e.printStackTrace();
                    
                    return false;
 
              }
    }

    @Override
    public List<Skill> getSkills(String username) {
        
        Person person = userRepository.findByUsername(username).getPerson();
        
        return skillRepository.findByPerson(person);
        
    }
    
}
