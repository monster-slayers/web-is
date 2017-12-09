package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

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
