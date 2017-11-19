package cz.muni.fi.pa165.monsterslayers.dto;

import cz.muni.fi.pa165.monsterslayers.entities.enums.PowerElement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * Data transfer object for creating hero to specified user.
 * 
 * @author Tomáš Richter
 */
public class CreateHeroDTO {
    private Long userId;

    @NotNull
    @Size(min = 4, max = 32)
    private String heroName;
    
    @NotEmpty(message = "Hero should control at least one power element. Otherwise, it's not a true hero :).")
    private Collection<PowerElement> elements = new HashSet<>();

    public CreateHeroDTO(Long userId, String heroName) {
        this.userId = userId;
        this.heroName = heroName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public void addElement(PowerElement element) {
        this.elements.add(element);
    }
}
