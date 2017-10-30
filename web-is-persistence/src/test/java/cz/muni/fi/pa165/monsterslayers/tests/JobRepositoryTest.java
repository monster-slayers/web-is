package cz.muni.fi.pa165.monsterslayers.tests;

import cz.muni.fi.pa165.monsterslayers.dao.ClientRequestRepository;
import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.dao.JobRepository;
import cz.muni.fi.pa165.monsterslayers.dao.UserRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.entities.enums.JobStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

/**
 * Tests for Job entity.
 *
 * @author Ondřej Budai
 */
@ContextConfiguration(locations = "/test-context.xml")
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class JobRepositoryTest {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private HeroRepository heroRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientRequestRepository clientRequestRepository;

    private Hero hero;
    private ClientRequest clientRequest;
    private User user;
    private Job sampleJob;

    @Before
    public void setup(){
        user = new User();
        user.setName("Sampleuser");
        user.setEmail("user@example.com");
        user.setPassword("12345");
        userRepository.save(user);

        hero = new Hero();
        hero.setHeroName("Samplehero");
        hero.setUser(user);
        heroRepository.save(hero);

        clientRequest = new ClientRequest();
        clientRequest.setTitle("Sampleclientrequest");
        clientRequest.setClient(user);
        clientRequestRepository.save(clientRequest);

        sampleJob = new Job();
        sampleJob.setAssignee(hero);
        sampleJob.setClientRequest(clientRequest);
        sampleJob.setStatus(JobStatus.ON_MISSION);
        sampleJob.setEvaluation(66);
        jobRepository.save(sampleJob);
    }

    @Test(expected = ConstraintViolationException.class)
    public void jobWithoutClientRequest(){
        Job job = new Job();
        job.setAssignee(hero);
        jobRepository.save(job);
    }

    @Test(expected = ConstraintViolationException.class)
    public void jobWithoutHero(){
        Job job = new Job();
        job.setClientRequest(clientRequest);
        jobRepository.save(job);
    }

    @Test
    public void clientRequestTest(){
        Assert.assertEquals(sampleJob.getClientRequest(), clientRequest);
    }

    @Test
    public void clientAssigneeTest(){
        Assert.assertEquals(sampleJob.getAssignee(), hero);
    }

    @Test
    public void clientJobStatusTest(){
        Assert.assertEquals(sampleJob.getStatus(), JobStatus.ON_MISSION);
    }

    @Test
    public void clientEvaluationTest(){
        Assert.assertEquals(sampleJob.getEvaluation(), new Integer(66));
    }

    @Test
    public void equalityTest(){
        Job job2 = new Job();
        job2.setClientRequest(clientRequest);
        job2.setAssignee(hero);
        Assert.assertEquals(sampleJob, job2);

        job2.setClientRequest(new ClientRequest());
        Assert.assertNotEquals(sampleJob, job2);

        job2.setClientRequest(clientRequest);
        job2.setAssignee(new Hero());
        Assert.assertNotEquals(sampleJob, job2);

        Assert.assertEquals(sampleJob, sampleJob);
        Assert.assertNotEquals(sampleJob, null);
        Assert.assertNotEquals(sampleJob, hero);
    }

    @Test
    public void hashTest(){
        Job job2 = new Job();
        job2.setClientRequest(clientRequest);
        job2.setAssignee(hero);
        Assert.assertEquals(sampleJob.hashCode(), job2.hashCode());
    }
}
