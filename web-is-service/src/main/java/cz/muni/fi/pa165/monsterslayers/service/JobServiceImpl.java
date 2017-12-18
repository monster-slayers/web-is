package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.JobRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.exception.MonsterSlayersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Job service implementation.
 *
 * @author David Kizivat
 */
@Service
public class JobServiceImpl implements JobService {

    private JobRepository repository;

    @Autowired
    public JobServiceImpl(JobRepository repository) {
        this.repository = repository;
    }

    @Override
    public Job getJobById(Long id) {
        try {
            return repository.findOne(id);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find job with id " + id.toString() + ".",
                    e
            );
        }
    }

    @Override
    public Collection<Job> getJobsByAssignee(Hero assignee) {
        try {
            return repository.findAllByAssignee(assignee);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find jobs with specified assignee.",
                    e
            );
        }
    }

    @Override
    public Iterable<Job> getAllJobs() {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot retrieve all jobs.",
                    e
            );
        }
    }

    @Override
    public Long saveJob(Job job) {
        try {
            return repository.save(job).getId();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot save a job.",
                    e
            );
        }
    }
}
