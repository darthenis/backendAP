
package portfolios.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
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
import java.io.Serializable;


@Entity
@Setter
@Getter
@Table(name = "messages")
public class PrivateMessages implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name="name", length = 30)
    private String name;
    
    @Column(name="email", length = 30)
    private String email;
    
    @Column(name="message")
    private String message;
    
    @Column(name="seen")
    private boolean seen;
    
    @Column(name="created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_person")
    @JsonIgnore
    private Person person;
    
    public PrivateMessages(){};
    
}
