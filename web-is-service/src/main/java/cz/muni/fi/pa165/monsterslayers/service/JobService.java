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
    Job getJobById(Long id);
    Collection<Job> getJobsByAssignee(Hero assignee);
    Iterable<Job> getAllJobs();
    Long createJob(Job job);
    void updateJob(Job job);

}
