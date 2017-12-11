package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.user.ChangeUserImageDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserLoginDTO;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;

import java.util.Collection;

/**
 * Facade layer interface for user
 *
 * @author Tomáš Richter
 */
public interface UserFacade {
    /**
     * Gets user with specific id as DTO
     *
     * @param userId specific id
     * @return single DTO
     */
    UserDTO getUserById(Long userId);

    /**
     * Gets user with specific email
     *
     * @param userEmail specific email
     * @return single DTO
     */
    UserDTO getUserByEmail(String userEmail);

    /**
     * Gets all users as collection of DTOs
     *
     * @return collection of DTOs
     */
    Collection<UserDTO> getAllUsers();

    /**
     * Gets all users with given name as collection of DTOs
     *
     * @param userName given name
     * @return collection of DTOs
     */
    Collection<UserDTO> getUsersByName(String userName);

    /**
     * Creates user in system according to DTO and adds password
     *
     * @param userDTO DTO of user
     * @param unencryptedPassword password will by encrypted on service layer
     */
    void registerUser (UserDTO userDTO, String unencryptedPassword);

    /**
     * Authenticates user according to special lofin DTO
     *
     * @param userLoginDTO DTO with email and password
     * @return true if user can be authenticated, false otherwise
     */
    boolean authenticateUser (UserLoginDTO userLoginDTO);

    /**
     * Checks if user has manager rights
     *
     * @param userDTO DTO of user
     * @return true if user has manager rights, false otherwise
     */
    boolean hasUserManagerRights(UserDTO userDTO);

    /**
     * Changes image of user according to special DTO
     *
     * @param changeUserImageDTO DTO for changing user image
     */
    void changeUserImage(ChangeUserImageDTO changeUserImageDTO);

    /**
     * Changes rights level of user to given rights level
     * @param userId id of user
     * @param rightsLevel new rights level
     */
    void changeRightsLevel(Long userId, RightsLevel rightsLevel);
}
