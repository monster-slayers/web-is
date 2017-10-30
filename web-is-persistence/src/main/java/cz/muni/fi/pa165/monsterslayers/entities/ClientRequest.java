package cz.muni.fi.pa165.monsterslayers.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * Basic entity class - ClientRequest
 * @author Ondřej Budai
 */
@Entity
public class ClientRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String title;

    @NotNull
    @ManyToOne(targetEntity = User.class)
    private User client;

    private String location;

    private String description;

    @ManyToMany(targetEntity = MonsterType.class)
    private Map<MonsterType, Integer> killList = new HashMap<>();

    private BigDecimal reward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addToKillList(MonsterType monsterType, int count){
        killList.put(monsterType, count);
    }

    public boolean isInKillList(MonsterType monsterType){
        return killList.containsKey(monsterType);
    }

    public Integer getCountFromKillList(MonsterType monsterType){
        return killList.get(monsterType);
    }

    public void removeFromKillList(MonsterType monsterType){
        killList.remove(monsterType);
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ClientRequest))
            return false;
        ClientRequest other = (ClientRequest) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.getTitle()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(title);
        return result;
    }
}
