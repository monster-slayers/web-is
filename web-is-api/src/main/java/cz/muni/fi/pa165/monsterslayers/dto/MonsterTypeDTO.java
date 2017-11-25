package cz.muni.fi.pa165.monsterslayers.dto;

import cz.muni.fi.pa165.monsterslayers.entities.enums.PowerElement;

import java.util.HashSet;
import java.util.Set;

/**
 * DTO for MonsterType.
 *
 * @author Ondrej Budai
 */
public class MonsterTypeDTO {
    private Long id;
    private String name;
    private String food;
    private Set<PowerElement> weaknesses = new HashSet<>();

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

    public Set<PowerElement> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(Set<PowerElement> weaknesses) {
        this.weaknesses = weaknesses;
    }
}
