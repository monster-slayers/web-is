package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.entities.enums.PowerElement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
    private User user;

    @NotNull
    @Column(nullable = false, unique = true)
    private String heroName;

    @ElementCollection(targetClass=PowerElement.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="Hero_Elements")
    private Collection<PowerElement> elements = new HashSet<>();

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

    public Collection<PowerElement> getElements() {
        return Collections.unmodifiableCollection(elements);
    }

    public void setElements(Collection<PowerElement> elements) {
        this.elements = elements;
    }

    public void addElement(PowerElement element) {
        elements.add(element);
    }

    public void removeElement(PowerElement element) {
        elements.remove(element);
    }

    public boolean hasElement(PowerElement element) {
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
        final Hero other = (Hero) o;
        if (!Objects.equals(this.heroName, other.getHeroName())) {
            return false;
        }
        return Objects.equals(this.user, other.getUser());
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(user);
        hash = 71 * hash + Objects.hashCode(heroName);
        return hash;
    }
}
