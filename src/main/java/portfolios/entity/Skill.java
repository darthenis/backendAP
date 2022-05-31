
package portfolios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
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
@Table(name="skills")
public class Skill implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name", length = 5)
    private String name;
    
    @Column(name = "porcent")
    private int porcent;
    
    @Column(name = "order_list")
    private int order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person")
    @JsonIgnore
    private Person person;
    
    
    public Skill(){}
         
}
