package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.ModifyHeroDTO;
import java.util.Collection;

/**
 * Facade layer interface for hero
 * 
 * @author Tomáš Richter
 */
public interface HeroFacade {
    HeroDTO getHeroById(Long heroId);
    HeroDTO getHeroByName(String heroName);
    Collection<HeroDTO> getAllHeroes();
    void removeHero(HeroDTO heroDTO);
    void editHero(ModifyHeroDTO modifyHeroDTO);
    void createHero(CreateHeroDTO heroDTO);
}
