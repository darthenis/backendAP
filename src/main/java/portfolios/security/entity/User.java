
package portfolios.security.entity;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn; 
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import portfolios.entity.Person;

@Getter @Setter
@Entity
@Table(name = "user")
public class User implements Serializable {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        
        @NotNull
        @Column(unique = true, name = "username")
        private String username;
        
        @NotNull
        @Column(name = "password")
        private String password;
        
        @NotNull
        @Column(name = "email")
        private String email;
        
        @Column(name = "enabled")
        private boolean enabled = false;
        
        @NotNull
        @ManyToMany 
        @JoinTable( name = "users_roles", 
            joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id")) 
        private Collection<Role> roles;
        
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "id_person")
        private Person person;
        
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "id_recovery")
        RecoveryPass recovery;
    
        public User(){}

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
        
        

        
        
        
}
