package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.facade.HeroFacade;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
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
    public void editHero(HeroDTO heroDTO) {
        heroService.saveHero(mappingService.mapTo(heroDTO, Hero.class));
    }

    @Override
    public void createHero(CreateHeroDTO createHeroDTO) {
        Hero hero = new Hero();
        hero.setHeroName(createHeroDTO.getHeroName());
        hero.setUser(userService.findUserById(createHeroDTO.getUserId()));
        hero.setElements(createHeroDTO.getElements());
        heroService.saveHero(hero);
    }
}
