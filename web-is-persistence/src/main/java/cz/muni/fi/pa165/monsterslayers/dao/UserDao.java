package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.User;
import java.util.List;

/**
 * User's data access object interface
 * 
 * @author Tomáš Richter 
 */
public interface UserDao {
    /**
     * Creates new User
     * @param user User to create
     */
    public void create(User user);

    /**
     * Deletes User
     * @param user User to delete
     */
    public void delete(User user);
    
    /**
     * Finds all Users
     * @return list of all Users
     */
    public List<User> findAll();
    
    /**
     * Finds User by its id
     * @param id id of User 
     * @return found User
     */
    public User findById(Long id);
    
    /**
     * Finds User by its email (used because it is unique)
     * @param email email of User
     * @return found User
     */
    public User findByEmail(String email);
}
