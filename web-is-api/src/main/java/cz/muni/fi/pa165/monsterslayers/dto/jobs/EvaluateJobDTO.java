package cz.muni.fi.pa165.monsterslayers.dto.jobs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DTO for evaluating a Job.
 *
 * @author David Kizivat
 */
public class EvaluateJobDTO {
    private Long jobId;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer evaluation;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EvaluateJobDTO that = (EvaluateJobDTO) o;

        if (!getJobId().equals(that.getJobId())) return false;
        return getEvaluation().equals(that.getEvaluation());
    }

    @Override
    public int hashCode() {
        int result = getJobId().hashCode();
        result = 31 * result + getEvaluation().hashCode();
        return result;
    }
}
