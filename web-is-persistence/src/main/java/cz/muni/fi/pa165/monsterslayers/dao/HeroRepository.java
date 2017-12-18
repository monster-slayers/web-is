package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Hero's repository interface extending spring CrudRepository
 *
 * @author Tomáš Richter
 */
public interface HeroRepository extends CrudRepository<Hero, Long> {
    /**
     * Finds hero with specified hero name (hero name is unique)
     * @param heroName specified hero name
     * @return Hero with specified hero name
     */
    Hero findByHeroName(String heroName);

    /**
     * Finds hero of specified user.
     * @param user specified user
     * @return Hero with assigned specified user
     */
    Hero findByUser(User user);
}
