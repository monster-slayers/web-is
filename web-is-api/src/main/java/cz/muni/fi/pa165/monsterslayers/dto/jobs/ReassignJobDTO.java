package cz.muni.fi.pa165.monsterslayers.dto.jobs;

/**
 * DTO for reassigning a Job.
 *
 * @author David Kizivat
 */
public class ReassignJobDTO {
    private Long jobId;
    private Long newHeroId;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getNewHeroId() {
        return newHeroId;
    }

    public void setNewHeroId(Long newHeroId) {
        this.newHeroId = newHeroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReassignJobDTO that = (ReassignJobDTO) o;

        if (!getJobId().equals(that.getJobId())) return false;
        return getNewHeroId().equals(that.getNewHeroId());
    }

    @Override
    public int hashCode() {
        int result = getJobId().hashCode();
        result = 31 * result + getNewHeroId().hashCode();
        return result;
    }
}
