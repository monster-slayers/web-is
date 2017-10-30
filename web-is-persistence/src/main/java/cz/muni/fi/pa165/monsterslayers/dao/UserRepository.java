package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User's repository interface extending spring CrudRepository
 *
 * @author Tomáš Richter
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Finds users with specified name (name is not unique, so list is returned)
     * @param name specified name
     * @return list of users with specified name
     */
    List<User> findByName(String name);
    
    /**
     * Finds user with specified email (email is unique)
     * @param email specified 
     * @return user with specified name
     */
    User findByEmail(String email);
}
