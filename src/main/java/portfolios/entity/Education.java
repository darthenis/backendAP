
package portfolios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name = "education")
public class Education implements Serializable{

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "date_init")
    private LocalDate initDate;
    
    @Column(name="date_end")
    private LocalDate endDate;
    
    @Column(name = "career")
    private String career;
    
    @Column(name = "order_list")
    private int order;
    
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_person")
    @JsonIgnore
    private Person person;
   
    public Education(){}

    
    
}
