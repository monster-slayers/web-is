package cz.muni.fi.pa165.monsterslayers.entities;

import cz.muni.fi.pa165.monsterslayers.entities.enums.JobStatus;
import javax.persistence.Entity;
import javax.persistence.*;

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

    /*
    TODO: ClientRequest (uncomment after entity is created)
    
    @ManyToOne
    @Column(nullable = false)
    private ClientRequest clientRequest;
    
    */
    
    @ManyToOne
    @Column(nullable = false)
    private Hero assignee;
    
    private JobStatus status;
    
    private Integer evaluation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
