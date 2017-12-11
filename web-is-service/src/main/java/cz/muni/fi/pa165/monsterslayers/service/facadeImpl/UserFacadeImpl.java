package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.user.ChangeUserImageDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserLoginDTO;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;
import cz.muni.fi.pa165.monsterslayers.facade.UserFacade;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        userService.registerUser(user, unencryptedPassword);
        userDTO.setId(user.getId());
    }

    @Override
    public boolean authenticateUser(UserLoginDTO userLoginDTO) {
        return userService.authenticateUser(userService.findUserByEmail(userLoginDTO.getEmail()), userLoginDTO.getPassword());
    }

    @Override
    public boolean hasUserManagerRights(UserDTO userDTO) {
        User user = mappingService.mapTo(userDTO, User.class);
        return userService.hasUserManagerRights(user);
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
