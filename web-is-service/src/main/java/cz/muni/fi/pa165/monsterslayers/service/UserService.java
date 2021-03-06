package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for a service access to User entity
 *
 * @author Tomáš Richter
 */
@Service
public interface UserService {
    /**
     * Finds user by given id
     *
     * @param id given id
     * @return found user entity
     */
    User findUserById(Long id);

    /**
     * Finds all users
     *
     * @return iterable collection of user entities
     */
    Iterable<User> getAllUsers();

    /**
     * Finds all users with given name
     *
     * @param name given user name
     * @return list of found users
     */
    List<User> findUsersByName(String name);

    /**
     * Finds user with specific email
     *
     * @param email email of user
     * @return found user entity
     */
    User findUserByEmail(String email);

    /**
     * Saves user entity
     *
     * @param user user entity that will be saved
     *
     * @return ID of newly created user
     */
    Long saveUser(User user);

    /**
     * Registers new user with password
     *
     * @param user new user
     */
    void registerUser (User user);

    /**
     * Checks if given user has at least manager rights
     *
     * @param user given user
     * @return true if user has manager rights, false otherwise
     */
    boolean hasManagerRights(User user);
    
    /**
     * Checks if given user has at least hero rights
     *
     * @param user given user
     * @return true if user has manager rights, false otherwise
     */
    boolean hasHeroRights(User user);

    /**
     * Edits image of given user
     *
     * @param user given user
     * @param image new image
     * @param imageMimeType new mime type string
     */
    void editUserImage(User user, byte[] image, String imageMimeType);
}
