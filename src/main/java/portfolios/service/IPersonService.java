
package portfolios.service;

import portfolios.dto.PersonDto;
import portfolios.entity.Person;



public interface IPersonService {
    
      public void edit(Person person, PersonDto personDto);
      
      public void delete(Person person);

      public void createPerson(Person person);
}
