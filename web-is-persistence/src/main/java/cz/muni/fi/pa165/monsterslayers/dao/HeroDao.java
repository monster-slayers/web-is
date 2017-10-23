package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import java.util.List;

/**
 * Hero's data access object interface
 * 
 * @author Tomáš Richter 
 */
public interface HeroDao {
    /**
     * Creates new Hero
     * @param hero Hero to create
     */
    public void create(Hero hero);
    
    /**
     * Deletes Hero
     * @param hero Hero to delete
     */
    public void delete(Hero hero);
    
    /**
     * Finds all Heroes
     * @return list of all Heroes
     */
    public List<Hero> findAll();
    
    /**
     * Finds Hero by its id
     * @param id id of Hero 
     * @return found Hero
     */
    public Hero findById(Long id);
}
