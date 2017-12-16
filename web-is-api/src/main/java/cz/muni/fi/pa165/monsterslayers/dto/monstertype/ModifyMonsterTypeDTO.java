package cz.muni.fi.pa165.monsterslayers.dto.monstertype;

import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import java.util.Collection;
import java.util.HashSet;

/**
 * DTO for modifying an existing MonsterType.
 *
 * @author Ondrej Budai
 */
public class ModifyMonsterTypeDTO {
    private Long id;
    private String name;
    private String food;
    private Collection<PowerElement> newWeaknesses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Collection<PowerElement> getNewWeaknesses() {
        return newWeaknesses;
    }

    public void setNewWeaknesses(Collection<PowerElement> newWeaknesses) {
        this.newWeaknesses = newWeaknesses;
    }
}
