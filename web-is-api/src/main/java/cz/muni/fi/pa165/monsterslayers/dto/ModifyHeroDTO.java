package cz.muni.fi.pa165.monsterslayers.dto;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import java.util.Collection;
import java.util.HashSet;
import javax.validation.constraints.Size;
/**
 * Data transfer object for modification of some hero's properties
 *
 * @author Tomáš Richter
 */
public class ModifyHeroDTO {
    private Long heroId;
    
    @Size(min = 4, max = 32)
    private String newHeroName;
    
    @Size(min = 1)
    private Collection<PowerElement> newElements = new HashSet<>();

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    public String getNewHeroName() {
        return newHeroName;
    }

    public void setNewHeroName(String newHeroName) {
        this.newHeroName = newHeroName;
    }

    public Collection<PowerElement> getNewElements() {
        return newElements;
    }

    public void setNewElements(Collection<PowerElement> newElements) {
        this.newElements = newElements;
    }
    
    
}
