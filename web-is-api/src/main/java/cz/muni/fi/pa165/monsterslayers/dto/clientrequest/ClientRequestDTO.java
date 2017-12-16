package cz.muni.fi.pa165.monsterslayers.dto.clientrequest;

import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DTO for client request
 *
 * @author Maksym Tsuhui
 */
public class ClientRequestDTO {
    private Long id;
    private String title;
    private UserDTO client;
    private String location;
    private String description;

    private Map<MonsterTypeDTO, Integer> killList = new HashMap<>();

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

    public UserDTO getClient() {

        return client;
    }

    public void setClient(UserDTO client) {
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

    public void addToKillList(MonsterTypeDTO monsterType, int count){
        killList.put(monsterType, count);
    }

    public void removeFromKillList(MonsterTypeDTO monsterType){
        killList.remove(monsterType);
    }

    public Map<MonsterTypeDTO, Integer> getKillList() {
        return killList;
    }

    public void setKillList(Map<MonsterTypeDTO, Integer> killList) {
        this.killList = killList;
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
        if (!(obj instanceof ClientRequestDTO))
            return false;
        ClientRequestDTO other = (ClientRequestDTO) obj;
        return (Objects.equals(this.title, other.getTitle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Objects.hashCode(title);
        return result;
    }
}
