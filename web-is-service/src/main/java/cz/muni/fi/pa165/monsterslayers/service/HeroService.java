package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomáš Richter
 */
@Service
public interface HeroService {
    //Basic crud operations
    Hero findById(Long id);
    Iterable<Hero> findAll();
    void createOrUpdateIfExists(Hero hero);
    void remove(Hero hero);
    
    //Custom crud operations
    Hero findByHeroName(String heroName);
    
    //Business methods
}
