package cz.muni.fi.pa165.monsterslayers.dto.jobs;

/**
 * DTO for Job creation.
 *
 * @author David Kizivat
 */
public class CreateJobDTO {
    private Long clientRequestId;
    private Long heroId;

    public Long getClientRequestId() {
        return clientRequestId;
    }

    public void setClientRequestId(Long clientRequestId) {
        this.clientRequestId = clientRequestId;
    }

    public Long getHeroId() {
        return heroId;
    }

    public void setHeroId(Long heroId) {
        this.heroId = heroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateJobDTO that = (CreateJobDTO) o;

        if (!getClientRequestId().equals(that.getClientRequestId())) return false;
        return getHeroId().equals(that.getHeroId());
    }

    @Override
    public int hashCode() {
        int result = getClientRequestId().hashCode();
        result = 31 * result + getHeroId().hashCode();
        return result;
    }
}
