package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.entities.enums.PowerElement;
import java.util.Collection;
import org.springframework.stereotype.Service;

/**
 * Interface for a service access to Hero entity
 *
 * @author Tomáš Richter
 */
@Service
public interface HeroService {
    Hero findHeroById(Long id);
    Iterable<Hero> getAllHeroes();
    void removeHero(Hero hero);
    void saveHero(Hero hero);
    Hero findHeroByName(String heroName);
    PowerElementsMatch countHeroSuitabilityAgainstMonsterType (Hero hero, MonsterType monsterType);
}
