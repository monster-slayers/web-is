package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.entities.enums.Elements;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

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

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String heroName;

    @ElementCollection(targetClass=Elements.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="Hero_Elements")
    private Collection<Elements> elements = new HashSet<>();

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

    public Collection<Elements> getElements() {
        return Collections.unmodifiableCollection(elements);
    }

    public void setElements(Collection<Elements> elements) {
        this.elements = elements;
    }

    public void addElement(Elements element) {
        elements.add(element);
    }

    public void removeElement(Elements element) {
        elements.remove(element);
    }

    public boolean hasElement(Elements element) {
        return elements.contains(element);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hero)) {
            return false;
        }
        Hero other = (Hero) o;
        if (other.user == null) {
            if (this.user != null) {
                return false;
            }
        } else if (other.heroName == null) {
            if (this.heroName != null) {
                return false;
            }
        }else if (!this.user.equals(other.user) || !this.heroName.equals(other.heroName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(user);
        hash = 71 * hash + Objects.hashCode(heroName);
        return hash;
    }
}
