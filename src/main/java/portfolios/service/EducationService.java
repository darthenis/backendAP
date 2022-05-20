
package portfolios.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import portfolios.dto.EducationDto;
import portfolios.entity.Education;
import portfolios.entity.Person;
import portfolios.repository.EducationRepository;
import portfolios.security.repository.UserRepository;

@Service
public class EducationService implements IEducationService{
    
    @Autowired
    EducationRepository educationRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public boolean edit(Person person, List<EducationDto> educationsDto) {
        
       List<Education> educations = new ArrayList<>();

       for(EducationDto e : educationsDto){
       
       try{
            
                Education education = educationRepository.findById(e.getId());
                
                if(person.getId() == education.getPerson().getId()){
                
                education.setTitle(e.getTitle());
                education.setName(e.getName());
                education.setCareer(e.getCareer());
                education.setInitDate(e.getInitDate());
                education.setEndDate(e.getEndDate());
                education.setLogoUrl(e.getLogoUrl());
                education.setPerson(person);
                education.setOrder(e.getOrder());
                
                educations.add(education);
           
                } else {
        
                    return false;
        
                }
                
          } catch (Exception error){  
                 error.printStackTrace();
                 return false;
   
            }  
       
       }
       
       educationRepository.saveAll(educations);
       
       
       
       return true;
        
    }
    
    
    
    @Override
    public void create(Person person ,EducationDto educationDto)throws Exception {
        
     try{
            
                Education education = new Education();
                
                education.setTitle(educationDto.getTitle());
                education.setName(educationDto.getName());
                education.setCareer(educationDto.getCareer());
                education.setInitDate(educationDto.getInitDate());
                education.setEndDate(educationDto.getEndDate());
                education.setLogoUrl(educationDto.getLogoUrl());
                education.setPerson(person);
        
                educationRepository.save(education);
         
     }catch(Exception e){
         e.printStackTrace();
         throw new Exception(e);
     }   
        

        
    
    }

    @Override
    public boolean delete(Person person, int eduId) {
        
            
  
          try {
              
                    Education education = educationRepository.findById(eduId);

                    if(person.getId() == education.getPerson().getId()){
                    
                        educationRepository.delete(education);
                        
                        return true;
                    
                    }
                    
                    return false;
              
              
              } catch (Exception e){
              
                    e.printStackTrace();
                    
                    return false;
 
              }
          
    }

    @Override
    public List<Education> getEducations(String username) {
        
        return educationRepository.findByPerson(userRepository.findByUsername(username).getPerson());
        
    }

    

    
    
}
