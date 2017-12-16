package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.JobRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Job service tests
 *
 * @author Tomáš Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class JobServiceTest {
    @Mock
    private JobRepository jobRepository;

    @Autowired
    @InjectMocks
    private JobService jobService;

    @Mock
    private Hero assignee1;

    @Mock
    private Hero assignee2;

    @Mock
    private ClientRequest clientRequest;

    private Job job1;
    private Job job2;
    private Collection<Job> jobs;
    private boolean initialized = false;

    @Before
    public void setup(){
        if (initialized) {
            return;
        }
        MockitoAnnotations.initMocks(this);

        initialized = true;
        job1 = new Job();
        job1.setId(1L);
        job1.setAssignee(assignee1);
        job1.setClientRequest(clientRequest);
        job1.setStatus(JobStatus.ASSIGNED);

        job2 = new Job();
        job2.setId(2L);
        job2.setAssignee(assignee2);

        jobs = new HashSet();
        jobs.add(job1);
        jobs.add(job2);
    }

    @Test
    public void getJobByIdExistingTest(){
        when(jobRepository.findOne(job1.getId())).thenReturn(job1);

        Job found = jobService.getJobById(1L);

        verify(jobRepository).findOne(1L);
        Assert.assertEquals(job1, found);
    }

    @Test
    public void getJobByNotExistingTest(){
        when(jobRepository.findOne(job1.getId())).thenReturn(null);

        Job found = jobService.getJobById(1L);

        verify(jobRepository).findOne(1L);
        Assert.assertNull(found);
    }

    @Test
    public void getAllJobsTest() {
        when(jobRepository.findAll()).thenReturn(jobs);

        Iterable<Job> found = jobService.getAllJobs();

        verify(jobRepository).findAll();
        Assert.assertEquals(jobs, found);
    }

    @Test
    public void getAllJobsByAssigneeTest() {
        Collection<Job> expected = new HashSet<>();
        expected.add(job1);

        when(jobRepository.findAllByAssignee(assignee1)).thenReturn(expected);

        Collection<Job> found = jobService.getJobsByAssignee(assignee1);

        verify(jobRepository).findAllByAssignee(assignee1);
        Assert.assertEquals(expected, found);
    }

    @Test
    public void getAllJobsByAssigneeNoSuchJobTest() {
        Collection<Job> expected = new HashSet<>();
        Hero other = new Hero();

        when(jobRepository.findAllByAssignee(other)).thenReturn(new HashSet());

        Collection<Job> found = jobService.getJobsByAssignee(other);

        verify(jobRepository).findAllByAssignee(other);
        Assert.assertEquals(expected, found);
    }

    @Test
    public void saveJobTest() {
        when(jobRepository.save(job1)).thenReturn(job1);

        Long id = jobService.saveJob(job1);

        verify(jobRepository).save(job1);
        Assert.assertEquals(id, job1.getId());
    }
}
