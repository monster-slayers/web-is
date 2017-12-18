package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.jobs.*;

import java.util.Collection;

/**
 * Facade layer interface for Job.
 *
 * @author David Kizivat
 */
public interface JobFacade {
    /**
     * Gets JobDTO from a Job entity based on given ID.
     *
     * @param id ID of the job to be found
     * @return JobDTO from the Job entity with given ID
     */
    JobDTO getJobById(Long id);

    /**
     * Gets JobDTOs by assignee.
     * 
     * @param assigneeId ID of the Hero whose JobsDTOs to get
     * @return Collection of JobDTOs assigned to the hero given by their DTO
     */
    Collection<JobDTO> getJobsByAssignee(Long assigneeId);

    /**
     * Gets DTO of all jobs.
     *
     * @return DTOs of all jobs tracked by the system
     */
    Collection<JobDTO> getAllJobs();

    /**
     * Creates a new Job based on given {@link CreateJobDTO}.
     *
     * @param dto {@link CreateJobDTO} with respective data
     * @return ID of the newly created Job
     */
    Long createJob(CreateJobDTO dto);

    /**
     * Reassigns a Job to new Hero.
     *
     * @param dto {@link ReassignJobDTO} with respective data
     */
    void reassignJob(ReassignJobDTO dto);

    /**
     * Adds evaluation to a Job.
     *
     * @param dto {@link EvaluateJobDTO} with respective data
     */
    void evaluateJob(EvaluateJobDTO dto);

    /**
     * Updates status of a Job.
     *
     * @param dto {@link UpdateJobStatusDTO} with respective data
     */
    void updateJobStatusDto(UpdateJobStatusDTO dto);
}
