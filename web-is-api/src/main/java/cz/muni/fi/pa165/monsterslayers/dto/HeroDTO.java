package cz.muni.fi.pa165.monsterslayers.dto;

import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Basic data transfer object for hero
 *
 * @author Tomáš Richter
 */
public class HeroDTO {
    private Long id;
    private UserDTO user;
    private String heroName;
    private Collection<PowerElement> elements = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public Collection<PowerElement> getElements() {
        return elements;
    }

    public void setElements(Collection<PowerElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof HeroDTO)) {
            return false;
        }
        final HeroDTO other = (HeroDTO) o;
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
