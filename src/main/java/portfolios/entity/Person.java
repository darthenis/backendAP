
package portfolios.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import portfolios.security.entity.User;


@Getter @Setter
@Entity
@Table(name = "person")
public class Person implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name", length = 13)
    private String name;
    
    @Column(name = "last_name", length = 13)
    private String lastName;
    
    @Column(name = "country", length = 15)
    private String country;
    
    @Column(name = "province_state", length = 15)
    private String state;
    
    @Column(name = "locality", length = 15)
    private String city;
    
    @Column(name = "pic_avatar")
    private String profile;
    
    @Column(name = "pic_banner")
    private String banner;
    
    @Column(name = "title", length = 30)
    private String title;
    
    @Column(name = "info", length = 250)
    private String info;
    
    @Column(name = "facebook")
    private String facebook;
    
    @Column(name = "twitter")
    private String twitter;
    
    @Column(name = "instagram")
    private String instagram;
    
    @Column(name = "linkedin")
    private String linkedin;
    
    @Column(name = "github")
    private String github;
    
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "person")
    private List<PrivateMessages> privateMessagess;
 
    
    public Person(){}

    public Person(String name, String lastName, String country, String provinceState, String locality, String profile, String banner, String title, String info, String facebook, String twitter, String instagram, String linkedin, String github, User user) {
        this.name = name;
        this.lastName = lastName;
        this.country = country;
        this.state = provinceState;
        this.city = locality;
        this.profile = profile;
        this.banner = banner;
        this.title = title;
        this.info = info;
        this.facebook = facebook;
        this.twitter = twitter;
        this.instagram = instagram;
        this.linkedin = linkedin;
        this.github = github;
        this.user = user;
    }

 
    
}
