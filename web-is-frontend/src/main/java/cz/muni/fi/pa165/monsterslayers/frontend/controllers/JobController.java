package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.jobs.JobDTO;
import cz.muni.fi.pa165.monsterslayers.facade.JobFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
