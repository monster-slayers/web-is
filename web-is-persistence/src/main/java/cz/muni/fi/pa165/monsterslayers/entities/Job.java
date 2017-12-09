package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Basic entity class - Job
 *
 * @author Tomáš Richter
 */
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(targetEntity = ClientRequest.class)
    private ClientRequest clientRequest;

    @NotNull
    @ManyToOne(targetEntity = Hero.class)
    private Hero assignee;

    @Enumerated(EnumType.ORDINAL)
    private JobStatus status = JobStatus.ASSIGNED;

    private Integer evaluation;

    public Job() {
        //hibernate requires it
    }

    public Job(ClientRequest clientRequest, Hero assignee) {
        this.clientRequest = clientRequest;
        this.assignee = assignee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientRequest getClientRequest() {
        return clientRequest;
    }

    public void setClientRequest(ClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public Hero getAssignee() {
        return assignee;
    }

    public void setAssignee(Hero assignee) {
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
        if (!(obj instanceof Job)) {
            return false;
        }
        final Job other = (Job) obj;
        if (!Objects.equals(this.clientRequest, other.getClientRequest())) {
            return false;
        }
        return (Objects.equals(this.assignee, other.getAssignee()));
    }
}
