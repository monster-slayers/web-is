package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.Job;

import java.util.List;

/**
 * Job's data access object.
 *
 * @author David Kizivat
 */
public interface JobDao {
    /**
     * Create new Job.
     *
     * @param job Job to be created
     */
    void create(Job job);

    /**
     * Deletes Job.
     *
     * @param job Job to be deleted
     */
    void delete(Job job);

    /**
     * Finds all Jobs.
     *
     * @return List of all Jobs
     */
    List<Job> findAll();

    /**
     * Finds Job by its Id.
     *
     * @param id Id of the Job to be found
     * @return Object of Job with the given Id
     */
    Job findById(Long id);
}
