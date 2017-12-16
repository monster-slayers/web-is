package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.UserRepository;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.exception.MonsterSlayersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of user service interface
 *
 * @author Tomáš Richter
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        try {
            return userRepository.findOne(id);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find user with id " + id.toString(),
                    e
            );
        }
    }

    @Override
    public Iterable<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find all users.",
                    e
            );
        }
    }

    @Override
    public void registerUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot register user.",
                    e
            );
        }
    }

    @Override
    public List<User> findUsersByName(String name) {
        try {
            return userRepository.findByName(name);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find user with name " + name + ".",
                    e
            );
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find user with e-mail " + email + ".",
                    e
            );
        }
    }

    @Override
    public Long saveUser(User user) {
        try {
            return userRepository.save(user).getId();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot save user.",
                    e
            );
        }
    }

    @Override
    public boolean hasManagerRights(User user) {
        try {
            return findUserById(user.getId()).getRightsLevel() == RightsLevel.MANAGER;
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot decide whether user has manager rights.",
                    e
            );
        }
    }

    @Override
    public boolean hasHeroRights(User user) {
        try {
            return findUserById(user.getId()).getRightsLevel() == RightsLevel.HERO || hasManagerRights(user);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot decide whether user has hero rights.",
                    e
            );
        }
    }

    @Override
    public void editUserImage(User user, byte[] image, String imageMimeType) {
        try {
            user.setImageMimeType(imageMimeType);
            user.setImage(image);
            userRepository.save(user);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot update user image.",
                    e
            );
        }
    }
}
