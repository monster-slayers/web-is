package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * JobRepository extends Spring's CrudRepository so Spring framework automatically
 * implements data access operations during runtime.
 *
 * @author David Kizivat
 */
public interface JobRepository extends CrudRepository<Job, Long> {
    /**
     * Finds a Job by given Hero assignee.
     *
     * @param assignee instance of type Hero
     * @return instance of type Job that is
     */
    Collection<Job> findAllByAssignee(Hero assignee);
}
