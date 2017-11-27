package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.JobRepository;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import org.springframework.stereotype.Service;

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
        return repository.findOne(id);
    }

    @Override
    public Collection<Job> getJobsByAssignee(Hero assignee) {
        return repository.findAllByAssignee(assignee);
    }

    @Override
    public Iterable<Job> getAllJobs() {
        return repository.findAll();
    }

    @Override
    public Long createJob(Job job) {
        return repository.save(job).getId();
    }

    @Override
    public void updateJob(Job job) {
        repository.save(job);
    }
}
