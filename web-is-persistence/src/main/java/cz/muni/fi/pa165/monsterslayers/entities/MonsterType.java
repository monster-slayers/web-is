package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

/**
 * Class representing monster type entity
 *
 * @author Maksym Tsuhui
 */
@Entity
public class MonsterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //hibernate needs it
    public MonsterType() {
    }

    public MonsterType(String name) {
        this.name = name;
    }

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String food;

    @ElementCollection(targetClass=PowerElement.class,fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="monsterType_weaknesses")
    private Collection<PowerElement> weaknesses = new HashSet<>();

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

    public Collection<PowerElement> getWeaknesses() {
        return Collections.unmodifiableCollection(weaknesses);
    }

    public void setWeaknesses(Collection<PowerElement> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public void addWeakness(PowerElement weakness) {
        this.weaknesses.add(weakness);
    }

    public boolean removeWeakness(PowerElement weakness) {
        return weaknesses.remove(weakness);
    }

    public boolean hasWeakness(PowerElement weakness) {
        return this.weaknesses.contains(weakness);
    }

    public int getCountOfWeaknesses() {
        return weaknesses.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof MonsterType)) return false;

        MonsterType that = (MonsterType) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
