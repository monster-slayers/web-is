package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.hero.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.ModifyHeroDTO;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.Job;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;
import cz.muni.fi.pa165.monsterslayers.enums.JobStatus;
import cz.muni.fi.pa165.monsterslayers.facade.HeroFacade;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.JobService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of hero facade interface.
 * Uses hero service and DTO.
 *
 * @author Tomáš Richter
 */

@Service
@Transactional
public class HeroFacadeImpl implements HeroFacade {

    @Autowired
    private HeroService heroService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private JobService jobService;

    @Override
    public HeroDTO getHeroById(Long heroId) {
        Hero found = heroService.findHeroById(heroId);
        if (found == null) {
            return null;
        }
        return mappingService.mapTo(found, HeroDTO.class);
    }

    @Override
    public HeroDTO getHeroByName(String heroName) {
        Hero found = heroService.findHeroByName(heroName);
        if (found == null) {
            return null;
        }
        return mappingService.mapTo(found, HeroDTO.class);
    }

    @Override
    public Collection<HeroDTO> getAllHeroes() {
        return mappingService.mapTo(heroService.getAllHeroes(), HeroDTO.class);
    }

    @Override
    public void removeHero(HeroDTO heroDTO) {
        heroService.removeHero(mappingService.mapTo(heroDTO, Hero.class));
    }

    @Override
    public Long createHero(CreateHeroDTO createHeroDTO) {
        Hero hero = mappingService.mapTo(createHeroDTO, Hero.class);
        hero.setUser(userService.findUserById(createHeroDTO.getUserId()));
        hero.setStatus(HeroStatus.ACTIVE);
        return heroService.saveHero(hero);
    }

    @Override
    public void editHero(ModifyHeroDTO modifyHeroDTO) {
        Hero hero = heroService.findHeroById(modifyHeroDTO.getHeroId());
        if (modifyHeroDTO.getNewHeroName() != null) {
            hero.setHeroName(modifyHeroDTO.getNewHeroName());
        }
        if (modifyHeroDTO.getNewElements() != null) {
            hero.setElements(modifyHeroDTO.getNewElements());
        }
        heroService.saveHero(hero);
    }

    @Override
    public void changeStatus(Long userId, HeroStatus userStatus) {
        Hero hero = heroService.findHeroById(userId);
        hero.setStatus(userStatus);
        heroService.saveHero(hero);

        if(userStatus == HeroStatus.DECEASED) {
            Collection<Job> jobs = jobService.getJobsByAssignee(hero);
            for(Job job: jobs){
                job.setStatus(JobStatus.UNSUCCESSFUL);
                jobService.updateJob(job);
            }
        }
    }
}
