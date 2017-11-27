package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;

import java.util.Collection;

/**
 * Interface for service access to Job entity.
 *
 * @author David Kizivat
 */
public interface JobService {
    /**
     * Finds job by given id
     * 
     * @param id given id
     * @return found job entity
     */
    Job getJobById(Long id);
    
    /**
     * Finds all jobs that are assigned to given hero
     * 
     * @param assignee given hero
     * @return Collection of job entities
     */
    Collection<Job> getJobsByAssignee(Hero assignee);
    
    /**
     * Finds all jobs
     * 
     * @return iterable collection of job entities 
     */
    Iterable<Job> getAllJobs();
    
    /**
     * Creates new job in system
     * 
     * @param job job entity that will be saved
     * @return id of created entity
     */
    Long createJob(Job job);
    
    /**
     * Update existing job
     * 
     * @param job job entity that will be saved
     */
    void updateJob(Job job);
}
