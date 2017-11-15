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
    void registerUser (User user, String password);
    boolean authenticateUser (User user, String password);
    List<User> findUserByName(String name);
    User findUserByEmail(String email);
}
