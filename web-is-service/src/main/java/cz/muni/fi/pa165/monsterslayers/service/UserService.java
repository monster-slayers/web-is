package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Interface for a service access to User entity
 * 
 * @author Tomáš Richter
 */
@Service
public interface UserService {
    User findUserById(Long id);
    Iterable<User> getAllUsers();
    List<User> findUsersByName(String name);
    User findUserByEmail(String email);
    
    void registerUser (User user, String password);
    boolean authenticateUser (User user, String password);
    
    boolean hasUserManagerRights(User user);
    boolean isUserActive(User user);
    void editUserImage(User user, byte[] image, String imageMimeType);
}
