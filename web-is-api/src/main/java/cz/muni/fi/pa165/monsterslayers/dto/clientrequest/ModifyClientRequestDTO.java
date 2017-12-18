package cz.muni.fi.pa165.monsterslayers.dto.clientrequest;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO for modification of some properties of client request
 *
 * @author Maksym Tsuhui
 */
public class ModifyClientRequestDTO {
    private Long clientRequestId;

    @Size(min = 4, max = 64)
    private String title;

    @Size(min = 4, max = 64)
    private String location;

    @Size(min = 4, max = 256)
    private String description;

    @Size(min = 1)
    private Map<Long, Integer> killList = new HashMap<>();

    @Min(100)
    private BigDecimal reward;

    public Long getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(Long clientRequestId) {
        this.clientRequestId = clientRequestId;
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
