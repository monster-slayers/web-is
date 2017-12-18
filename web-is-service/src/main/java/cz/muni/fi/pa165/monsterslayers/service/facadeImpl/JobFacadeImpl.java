package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.*;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import cz.muni.fi.pa165.monsterslayers.facade.JobFacade;
import cz.muni.fi.pa165.monsterslayers.service.ClientRequestService;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.JobService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implementation of the facade layer for Jobs.
 *
 * @author David Kizivat
 */

@Service
@Transactional
public class JobFacadeImpl implements JobFacade {

    @Autowired
    private JobService jobService;
    @Autowired
    private HeroService heroService;
    @Autowired
    private ClientRequestService clientRequestService;
    @Autowired
    private MappingService mappingService;

    @Override
    public JobDTO getJobById(Long id) {
        Job result = jobService.getJobById(id);
        if (result == null) {
            return null;
        }
        return mappingService.mapTo(result, JobDTO.class);
    }

    @Override
    public Collection<JobDTO> getJobsByAssignee(Long assigneeId) {
        Hero hero = heroService.findHeroById(assigneeId);
        return mappingService.mapTo(jobService.getJobsByAssignee(hero), JobDTO.class);
    }

    @Override
    public Collection<JobDTO> getAllJobs() {
        return mappingService.mapTo(jobService.getAllJobs(), JobDTO.class);
    }

    @Override
    public Long createJob(CreateJobDTO dto) {
        Job job = new Job();
        job.setClientRequest(clientRequestService.findClientRequestById(dto.getClientRequestId()));
        job.setAssignee(heroService.findHeroById(dto.getHeroId()));
        return jobService.saveJob(job);
    }

    @Override
    public void reassignJob(ReassignJobDTO dto) {
        Job job = jobService.getJobById(dto.getJobId());
        Hero newAssignee = heroService.findHeroById(dto.getNewHeroId());
        job.setAssignee(newAssignee);
        jobService.saveJob(job);
    }

    @Override
    public void evaluateJob(EvaluateJobDTO dto) {
        Job job = jobService.getJobById(dto.getJobId());
        Integer evaluation = dto.getEvaluation();
        job.setEvaluation(evaluation);
        jobService.saveJob(job);
    }

    @Override
    public void updateJobStatusDto(UpdateJobStatusDTO dto) {
        Job job = jobService.getJobById(dto.getJobId());
        JobStatus newStatus = dto.getNewStatus();
        job.setStatus(newStatus);
        jobService.saveJob(job);
    }

    @Override
    public JobDTO getByClientRequestId(Long id) {
        ClientRequest clientRequest = clientRequestService.findClientRequestById(id);
        Job job = jobService.getJobByClientRequest(clientRequest);
        return job != null ? mappingService.mapTo(jobService.getJobByClientRequest(clientRequest), JobDTO.class) : null;
    }
}
