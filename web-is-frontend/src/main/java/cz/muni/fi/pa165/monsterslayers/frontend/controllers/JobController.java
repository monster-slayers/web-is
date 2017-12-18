package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.CreateJobDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.EvaluateJobDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.JobDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.UpdateJobStatusDTO;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import cz.muni.fi.pa165.monsterslayers.facade.JobFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobFacade jobFacade;

    @GetMapping
    Collection<JobDTO> findAll(){
        return jobFacade.getAllJobs();
    }

    @PostMapping(value = "/create/{clientRequestId}/{assigneeId}")
    private void createJob(@PathVariable Long clientRequestId, @PathVariable Long assigneeId){
        CreateJobDTO dto = new CreateJobDTO();
        dto.setClientRequestId(clientRequestId);
        dto.setHeroId(assigneeId);
        jobFacade.createJob(dto);
    }

    @PutMapping(value = "/update-status/{id}/{status}")
    private void updateStatus(@PathVariable("id") Long id, @PathVariable("status") JobStatus newStatus){
        UpdateJobStatusDTO dto = new UpdateJobStatusDTO();
        dto.setJobId(id);
        dto.setNewStatus(newStatus);
        jobFacade.updateJobStatusDto(dto);
    }


    @PutMapping(value = "/update-evaluation/{id}/{status}")
    private void updateEvaluation(@PathVariable("id") Long id, @PathVariable("status") Integer evaluation){
        EvaluateJobDTO dto = new EvaluateJobDTO();
        dto.setJobId(id);
        dto.setEvaluation(evaluation);
        jobFacade.evaluateJob(dto);
    }
}
