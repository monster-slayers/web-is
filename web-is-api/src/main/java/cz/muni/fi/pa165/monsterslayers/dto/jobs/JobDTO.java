package cz.muni.fi.pa165.monsterslayers.dto.jobs;

import cz.muni.fi.pa165.monsterslayers.dto.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;

import java.util.Objects;

public class JobDTO {
    private Long id;

    private ClientRequestDTO clientRequest;

    private HeroDTO assignee;

    private JobStatus status;

    private Integer evaluation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientRequestDTO getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(ClientRequestDTO clientRequest) {
        this.clientRequest = clientRequest;
    }

    public HeroDTO getAssignee() {
        return assignee;
    }

    public void setAssignee(HeroDTO assignee) {
        this.assignee = assignee;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.clientRequest);
        hash = 83 * hash + Objects.hashCode(this.assignee);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof JobDTO)) {
            return false;
        }
        final JobDTO other = (JobDTO) obj;
        return Objects.equals(this.clientRequest, other.getClientRequest()) && (Objects.equals(this.assignee, other.getAssignee()));
    }
}
