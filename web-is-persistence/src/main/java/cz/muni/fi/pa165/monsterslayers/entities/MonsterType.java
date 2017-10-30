package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.entities.enums.Elements;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class MonsterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //hibernate needs it
    public MonsterType() {
    }

    //konstruktor, pri vytvoreni objektu nastavi hodnoty atributu
    public MonsterType(String name) {
        this.name = name;
    }

    @Column(nullable = false, unique = true)
    private String name;

    private String food;

    @ElementCollection(targetClass=Elements.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="monsterType_weaknesses")
    private Collection<Elements> weaknesses = new HashSet<>();

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

    public Collection<Elements> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(Collection<Elements> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public void addWeakness(Elements weakness) {
        this.weaknesses.add(weakness);
    }

    public void removeWeakness(Elements weakness) {
        this.weaknesses.remove(weakness);
    }

    public boolean hasWeakness(Elements weakness) {
        return this.weaknesses.contains(weakness);
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
        return getName().hashCode();
    }
}