package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.*;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import cz.muni.fi.pa165.monsterslayers.facade.JobFacade;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.JobService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.PowerElementsMatch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Map;

/**
 * Implementation of the facade layer for Jobs.
 *
 * @author David Kizivat
 */
public class JobFacadeImpl implements JobFacade {

    private final JobService jobService;
    private final HeroService heroService;
    private final MappingService mappingService;

    @Autowired
    public JobFacadeImpl(JobService jobService, HeroService heroService, MappingService mappingService) {
        this.jobService = jobService;
        this.heroService = heroService;
        this.mappingService = mappingService;
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job result = jobService.getJobById(id);
        if (result == null) {
            return null;
        }
        return mappingService.mapTo(result, JobDTO.class);
    }

    @Override
    public Collection<JobDTO> getJobsByAssignee(HeroDTO assignee) {
        Hero hero = mappingService.mapTo(assignee, Hero.class);
        return mappingService.mapTo(jobService.getJobsByAssignee(hero), JobDTO.class);
    }

    @Override
    public Collection<JobDTO> getAllJobs() {
        return mappingService.mapTo(jobService.getAllJobs(), JobDTO.class);
    }

    @Override
    public Long createJob(CreateJobDTO dto) {
        return jobService.createJob(mappingService.mapTo(dto, Job.class));
    }

    @Override
    public void reassignJob(ReassignJobDTO dto) {
        Job job = jobService.getJobById(dto.getJobId());
        Hero newAssignee = heroService.findHeroById(dto.getNewHeroId());
        job.setAssignee(newAssignee);
        jobService.updateJob(job);
    }

    @Override
    public void evaluateJob(EvaluateJobDTO dto) {
        Job job = jobService.getJobById(dto.getJobId());
        Integer evaluation = dto.getEvaluation();
        job.setEvaluation(evaluation);
        jobService.updateJob(job);
    }

    @Override
    public void updateJobStatusDto(UpdateJobStatusDTO dto) {
        Job job = jobService.getJobById(dto.getJobId());
        JobStatus newStatus = dto.getNewStatus();
        job.setStatus(newStatus);
        jobService.updateJob(job);
    }

    @Override
    public HeroDTO getBestHeroForJob(JobDTO dto) {
        Map<MonsterTypeDTO, Integer> killList = dto.getClientRequest().getKillList();

        PowerElementsMatch bestMatch = new PowerElementsMatch(0, Integer.MAX_VALUE);
        Hero bestHero = new Hero();

        for (Map.Entry<MonsterTypeDTO, Integer> entry : killList.entrySet()) {
            MonsterType monsterType = mappingService.mapTo(entry.getKey(), MonsterType.class);
            for (Hero hero : heroService.getAllHeroes()) {
                PowerElementsMatch match = heroService.countHeroSuitabilityAgainstMonsterType(hero, monsterType);
                if (match.isMoreSuitable(bestMatch)) {
                    bestMatch = match;
                    bestHero = hero;
                }
            }
        }

        return mappingService.mapTo(bestHero, HeroDTO.class);
    }
}