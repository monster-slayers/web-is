package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import org.springframework.data.repository.CrudRepository;

/**
 * Job repository extends Spring's CrudRepository to automatically implement data access operations.
 *
 * @author David Kizivat
 */
public interface JobRepository extends CrudRepository<Job, Long> {
    Job getByAssignee(Hero assignee);
}
