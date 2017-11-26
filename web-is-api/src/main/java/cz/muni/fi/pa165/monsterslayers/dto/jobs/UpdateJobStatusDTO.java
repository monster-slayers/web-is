package cz.muni.fi.pa165.monsterslayers.dto.jobs;

import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;

import javax.validation.constraints.NotNull;

/**
 * DTO for updating status of a Job.
 *
 * @author David Kizivat
 */
public class UpdateJobStatusDTO {
    private Long jobId;

    @NotNull
    private JobStatus newStatus;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public JobStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(JobStatus newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateJobStatusDTO that = (UpdateJobStatusDTO) o;

        if (!getJobId().equals(that.getJobId())) return false;
        return getNewStatus() == that.getNewStatus();
    }

    @Override
    public int hashCode() {
        int result = getJobId().hashCode();
        result = 31 * result + getNewStatus().hashCode();
        return result;
    }
}
