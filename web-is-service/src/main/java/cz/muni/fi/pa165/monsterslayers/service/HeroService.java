package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
import org.springframework.stereotype.Service;

/**
 * Interface for a service access to Hero entity
 *
 * @author Tomáš Richter
 */
@Service
public interface HeroService {
    /**
     * Finds hero by given id
     *
     * @param id given id
     * @return found hero entity
     */
    Hero findHeroById(Long id);

    /**
     * Finds all heroes
     *
     * @return iterable collection of hero entities
     */
    Iterable<Hero> getAllHeroes();

    /**
     * Removes hero
     *
     * @param hero hero entity that will be removed
     */
    void removeHero(Hero hero);

    /**
     * Saves (creates or updates) hero
     *
     * @param hero hero entity that will be saved
     *
     * @return ID of newly created hero
     */
    Long saveHero(Hero hero);

    /**
     * Finds hero with given name
     *
     * @param heroName given hero name
     * @return found hero entity
     */
    Hero findHeroByName(String heroName);

    /**
     * Count hero suitability against monster type
     * (match between hero's skills and monster type's weaknesses)
     *
     * @param hero given hero
     * @param monsterType given monsterType
     * @return sauitability as PowerElementsMatch
     */
    PowerElementsMatch countHeroSuitabilityAgainstMonsterType (Hero hero, MonsterType monsterType);
}
