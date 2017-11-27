package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.*;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.JobService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class JobFacadeTest {
    @Mock
    private JobService jobService;

    @Mock
    private HeroService heroService;

    @Mock
    private MappingService mappingService;

    @Autowired
    @InjectMocks
    private JobFacade jobFacade;

    private boolean initialized = false;

    @Before
    public void setup() {
        // this is not optimal, but junit requires from method
        // annotated with @Before to be static
        // possibly better solution is to use testng instead...
        if (initialized) {
            return;
        }
        MockitoAnnotations.initMocks(this);

        initialized = true;
    }

    @Test
    public void testGetJobById(){
        Long id = 1L;

        Job testJob = new Job();
        when(jobService.getJobById(id)).thenReturn(testJob);

        JobDTO testjobDTO = new JobDTO();
        when(mappingService.mapTo(testJob, JobDTO.class)).thenReturn(testjobDTO);

        JobDTO actualJob = jobFacade.getJobById(id);

        Assert.assertEquals(testjobDTO, actualJob);
    }

    @Test
    public void testGetJobsByAssignee(){
        Long assigneeId = 1L;

        Hero assignee = new Hero();

        when(heroService.findHeroById(assigneeId)).thenReturn(assignee);

        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job());

        when(jobService.getJobsByAssignee(assignee)).thenReturn(jobList);

        List<JobDTO> jobDTOList = new ArrayList<>();
        jobDTOList.add(new JobDTO());

        when(mappingService.mapTo(jobList, JobDTO.class)).thenReturn(jobDTOList);

        Collection<JobDTO> actualJobs = jobFacade.getJobsByAssignee(assigneeId);

        Assert.assertThat(jobDTOList, IsIterableContainingInAnyOrder.containsInAnyOrder(actualJobs.toArray()));
    }

    @Test
    public void testGetAllJobs(){
        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job());
        jobList.add(new Job());

        when(jobService.getAllJobs()).thenReturn(jobList);

        List<JobDTO> jobDTOList = new ArrayList<>();
        jobDTOList.add(new JobDTO());
        jobDTOList.add(new JobDTO());

        when(mappingService.mapTo(jobList, JobDTO.class)).thenReturn(jobDTOList);

        Collection<JobDTO> actualJobs = jobFacade.getAllJobs();

        Assert.assertEquals(jobDTOList, actualJobs);
    }

    @Test
    public void testCreateJob(){

        CreateJobDTO createJobDTO = new CreateJobDTO();
        Long clientRequestId = 22L;
        createJobDTO.setClientRequestId(clientRequestId);
        Long heroId = 54L;
        createJobDTO.setHeroId(heroId);

        Job expectedJob = new Job();

        ClientRequest expectedClientRequest = new ClientRequest();
        expectedClientRequest.setId(clientRequestId);
        expectedJob.setClientRequest(expectedClientRequest);

        Hero expectedAssignee = new Hero();
        expectedAssignee.setId(heroId);
        expectedJob.setAssignee(expectedAssignee);

        when(mappingService.mapTo(createJobDTO, Job.class)).thenReturn(expectedJob);

        Long expectedId = 66L;
        when(jobService.createJob(expectedJob)).thenReturn(expectedId);

        Long actualJobId = jobFacade.createJob(createJobDTO);


        ArgumentCaptor<Job> jobCapture = ArgumentCaptor.forClass(Job.class);
        verify(jobService).createJob(jobCapture.capture());

        Job job = jobCapture.getValue();

        Assert.assertEquals(job.getClientRequest().getId(), clientRequestId);
        Assert.assertEquals(job.getAssignee().getId(), heroId);
        Assert.assertEquals(job.getStatus(), JobStatus.ASSIGNED);
        Assert.assertEquals(job.getEvaluation(), new Integer(0));
        Assert.assertEquals(expectedId, actualJobId);
    }

    @Test
    public void testReassignJob(){
        ReassignJobDTO reassignJobDTO = new ReassignJobDTO();
        Long jobId = 1L;
        reassignJobDTO.setJobId(jobId);
        Long newHeroId = 2L;
        reassignJobDTO.setNewHeroId(newHeroId);

        Job job = new Job();
        job.setId(jobId);
        when(jobService.getJobById(jobId)).thenReturn(job);

        Hero newHero = new Hero();
        newHero.setId(newHeroId);
        when(heroService.findHeroById(newHeroId)).thenReturn(newHero);

        jobFacade.reassignJob(reassignJobDTO);

        ArgumentCaptor<Job> jobCapture = ArgumentCaptor.forClass(Job.class);
        verify(jobService).updateJob(jobCapture.capture());

        Job actualJob = jobCapture.getValue();

        Assert.assertEquals(jobId, actualJob.getId());
        Assert.assertEquals(newHeroId, actualJob.getAssignee().getId());
    }

    @Test
    public void testEvaluateJob(){
        EvaluateJobDTO evaluateJobDTO = new EvaluateJobDTO();
        Long jobId = 88L;
        evaluateJobDTO.setJobId(jobId);
        Integer evaluation = 88;
        evaluateJobDTO.setEvaluation(evaluation);

        Job job = new Job();
        job.setId(jobId);
        when(jobService.getJobById(jobId)).thenReturn(job);

        jobFacade.evaluateJob(evaluateJobDTO);

        ArgumentCaptor<Job> jobCaptor = ArgumentCaptor.forClass(Job.class);
        verify(jobService).updateJob(jobCaptor.capture());

        Job actualJob = jobCaptor.getValue();

        Assert.assertEquals(jobId, actualJob.getId());
        Assert.assertEquals(evaluation, actualJob.getEvaluation());
    }

    @Test
    public void testUpdateJobStatus(){
        UpdateJobStatusDTO updateJobStatusDto = new UpdateJobStatusDTO();
        Long jobId = 88L;
        updateJobStatusDto.setJobId(jobId);
        JobStatus jobStatus = JobStatus.ON_MISSION;
        updateJobStatusDto.setNewStatus(jobStatus);

        Job job = new Job();
        job.setId(jobId);
        when(jobService.getJobById(jobId)).thenReturn(job);

        jobFacade.updateJobStatusDto(updateJobStatusDto);

        ArgumentCaptor<Job> jobCaptor = ArgumentCaptor.forClass(Job.class);
        verify(jobService).updateJob(jobCaptor.capture());

        Job actualJob = jobCaptor.getValue();

        Assert.assertEquals(jobId, actualJob.getId());
        Assert.assertEquals(jobStatus, actualJob.getStatus());
    }

    @Test
    public void testGetBestHeroForJob(){
        Long jobId = 77L;

        Job job = new Job();

        ClientRequest clientRequest = new ClientRequest();

        MonsterType monsterType1 = new MonsterType();
        monsterType1.addWeakness(PowerElement.GHOST);
        monsterType1.setName("Java");
        clientRequest.addToKillList(monsterType1, 2);

        MonsterType monsterType2 = new MonsterType();
        monsterType2.addWeakness(PowerElement.EARTH);
        monsterType2.setName("Oracle");
        clientRequest.addToKillList(monsterType2, 3);

        job.setClientRequest(clientRequest);

        when(jobService.getJobById(jobId)).thenReturn(job);


        List<Hero> heroes = new ArrayList<>();
        Hero hero1 = new Hero();
        hero1.addElement(PowerElement.EARTH);
        heroes.add(hero1);

        Hero hero2 = new Hero();
        hero2.addElement(PowerElement.POISON);

        when(heroService.getAllHeroes()).thenReturn(heroes);

        HeroDTO heroDTO = new HeroDTO();

        when(mappingService.mapTo(hero1, HeroDTO.class)).thenReturn(heroDTO);

        when(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType1)).thenReturn(new PowerElementsMatch(0, 1));
        when(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType2)).thenReturn(new PowerElementsMatch(1, 0));
        when(heroService.countHeroSuitabilityAgainstMonsterType(hero2, monsterType1)).thenReturn(new PowerElementsMatch(0, 1));
        when(heroService.countHeroSuitabilityAgainstMonsterType(hero2, monsterType2)).thenReturn(new PowerElementsMatch(0, 1));

        HeroDTO bestHeroForJob = jobFacade.getBestHeroForJob(jobId);

        Assert.assertEquals(heroDTO, bestHeroForJob);
    }
}
