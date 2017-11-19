package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.ChangeUserImageDTO;
import cz.muni.fi.pa165.monsterslayers.dto.UserDTO;
import cz.muni.fi.pa165.monsterslayers.dto.UserLoginDTO;
import java.util.Collection;

/**
 * Facade layer interface for user
 * 
 * @author Tomáš Richter
 */
public interface UserFacade {
    UserDTO getUserById(Long userId);
    UserDTO getUserByEmail(String userEmail);
    Collection<UserDTO> getAllUsers();
    Collection<UserDTO> getUsersByName(String userName);
    
    void registerUser (UserDTO userDTO, String unencryptedPassword);
    boolean authenticateUser (UserLoginDTO userLoginDTO);
    
    boolean hasUserManagerRights(UserDTO userDTO);
    boolean isUserActive(UserDTO userDTO);
    void changeUserImage(ChangeUserImageDTO changeUserImageDTO);
}
