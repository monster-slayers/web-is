package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.*;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import cz.muni.fi.pa165.monsterslayers.facade.JobFacade;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.JobService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return jobService.saveJob(mappingService.mapTo(dto, Job.class));
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
    public HeroDTO getBestHeroForJob(Long jobId) {
        Job job = jobService.getJobById(jobId);

        Map<MonsterType, Integer> killList = job.getClientRequest().getKillList();

        PowerElementsMatch bestMatch = new PowerElementsMatch(0, Integer.MAX_VALUE);
        Hero bestHero = new Hero();

        for (Hero hero : heroService.getAllHeroes()) {
            List<PowerElementsMatch> matches = new ArrayList<>();
            for (Map.Entry<MonsterType, Integer> entry : killList.entrySet()) {
                MonsterType monsterType = entry.getKey();
                matches.add(
                        heroService.countHeroSuitabilityAgainstMonsterType(hero, monsterType)
                                .multiplyByMonsterCount(entry.getValue())
                );
            }

            PowerElementsMatch match = PowerElementsMatch.sumMatches(matches);
            if (match.isMoreSuitable(bestMatch)) {
                bestMatch = match;
                bestHero = hero;
            }
        }

        return mappingService.mapTo(bestHero, HeroDTO.class);
    }
}
