package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.user.ChangeUserImageDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserLoginDTO;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.facade.UserFacade;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.PasswordService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Implementation of user facade interface.
 * Uses user service and DTO (DTOs for authetication and change image as well).
 *
 * @author Tomáš Richter
 */

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private PasswordService passwordService;


    @Override
    public UserDTO getUserById(Long userId) {
        User found = userService.findUserById(userId);
        if (found == null) {
            return null;
        }
        return mappingService.mapTo(found, UserDTO.class);
    }

    @Override
    public UserDTO getUserByEmail(String userEmail) {
        User found = userService.findUserByEmail(userEmail);
        if (found == null) {
            return null;
        }
        return mappingService.mapTo(found, UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return mappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public Collection<UserDTO> getUsersByName(String userName) {
        return mappingService.mapTo(userService.findUsersByName(userName), UserDTO.class);
    }

    @Override
    public void registerUser(UserDTO userDTO, String unencryptedPassword) {
        User user = mappingService.mapTo(userDTO, User.class);
        user.setPassword(passwordService.createHash(unencryptedPassword));
        userService.registerUser(user);
        userDTO.setId(user.getId());
    }

    @Override
    public boolean authenticateUser(UserLoginDTO userLoginDTO) {
        User user = userService.findUserByEmail(userLoginDTO.getEmail());
        String password = userLoginDTO.getPassword();
        String correctHash = user.getPassword();

        return passwordService.checkHash(password, correctHash);
    }

    @Override
    public boolean hasManagerRights(UserDTO userDTO) {
        User user = mappingService.mapTo(userDTO, User.class);
        return userService.hasManagerRights(user);
    }
    
    @Override
    public boolean hasHeroRights(UserDTO userDTO) {
        User user = mappingService.mapTo(userDTO, User.class);
        return userService.hasHeroRights(user);
    }

    @Override
    public void changeUserImage(ChangeUserImageDTO changeUserImageDTO) {
        User user = userService.findUserById(changeUserImageDTO.getUserId());
        userService.editUserImage(user, changeUserImageDTO.getImage(), changeUserImageDTO.getImageMimeType());
    }

    @Override
    public void changeRightsLevel(Long userId, RightsLevel rightsLevel) {
        User user = userService.findUserById(userId);
        user.setRightsLevel(rightsLevel);
        userService.saveUser(user);
    }
}
