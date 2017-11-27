package cz.muni.fi.pa165.monsterslayers.dto.monstertype;

import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;

/**
 * DTO for creating a new MonsterType.
 *
 * @author Ondrej Budai
 */
public class CreateMonsterTypeDTO {
    private String name;
    private String food;
    private PowerElement weakness;

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

    public PowerElement getWeakness() {
        return weakness;
    }

    public void setWeakness(PowerElement weakness) {
        this.weakness = weakness;
    }
}
