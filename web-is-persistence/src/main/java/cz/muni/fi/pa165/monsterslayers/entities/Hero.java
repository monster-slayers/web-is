package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.entities.enums.Elements;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Basic entity class - Hero
 *
 * @author David Kizivat
 */

@Entity
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = User.class)
    @Column(nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String heroName;

    @ManyToMany(targetEntity = Elements.class)
    private Set<Elements> elements = new HashSet<Elements>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public Set<Elements> getElements() {
        return elements;
    }

    public void setElements(Set<Elements> elements) {
        this.elements = elements;
    }
}
