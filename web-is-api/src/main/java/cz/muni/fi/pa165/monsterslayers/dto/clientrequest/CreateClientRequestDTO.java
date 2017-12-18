package cz.muni.fi.pa165.monsterslayers.dto.clientrequest;

import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO for client request create
 *
 * @author Maksym Tsuhui
 */
public class CreateClientRequestDTO {
    private Long clientId;

    @NotNull
    @Size(min = 4, max = 64)
    private String title;

    @NotNull
    @Size(min = 4, max = 64)
    private String location;

    @NotNull
    @Size(min = 4, max = 256)
    private String description;

    @Size(min = 1)
    private Map<Long, Integer> killList = new HashMap<>();

    @NotNull
    @Min(100)
    private BigDecimal reward;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Map<Long, Integer> getKillList() {
        return killList;
    }

    public void setKillList(Map<Long, Integer> killList) {
        this.killList = killList;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }
}
