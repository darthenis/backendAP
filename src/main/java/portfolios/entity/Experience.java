
package portfolios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import lombok.*;



@Getter
@Setter
@Entity
@Table(name = "experience")
public class Experience implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "job")
    private String job;

   
    @Column(name = "date_init")
    private LocalDate dateInit;
    
    @Column(name="date_end")
    private LocalDate dateEnd;
    
    @Column(name = "info")
    private String info;
    
    @Column(name="order_list")
    private int order;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_person")
    @JsonIgnore
    private Person person;
    

    public Experience(){}

    
    
}
