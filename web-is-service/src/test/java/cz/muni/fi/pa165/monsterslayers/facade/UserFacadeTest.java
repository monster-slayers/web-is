package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.*;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.enums.UserStatus;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for user facade tests
 *
 * @author Maksym Tsuhui
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class UserFacadeTest {

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserFacade userFacade;

    @Mock
    private MappingService mappingService;

    @Mock
    private UserLoginDTO userLoginDTO;

    @Mock
    private UserDTO userDTO;

    @Mock
    private ChangeUserImageDTO changeUserImageDTO;

    @Mock
    private User user;
    private boolean initialized = false;

    @Before
    public void setup(){
        if (initialized) {
            return;
        }
        MockitoAnnotations.initMocks(this);

        initialized = true;
    }

    @Test
    public void testGetUserById() {
        Long id = 1L;

        when(userService.findUserById(id)).thenReturn(user);
        when(mappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO found = userFacade.getUserById(id);

        verify(userService).findUserById(id);
        verify(mappingService).mapTo(user, UserDTO.class);
        Assert.assertEquals(userDTO,found);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "email@email.cz";
        when(userService.findUserByEmail(email)).thenReturn(user);
        when(mappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO found = userFacade.getUserByEmail(email);

        verify(userService).findUserByEmail(email);
        verify(mappingService).mapTo(user, UserDTO.class);
        Assert.assertEquals(userDTO,found);
    }

    @Test
    public void testGetAll() {
        Collection<User> list = Arrays.asList(user);
        Collection<UserDTO> listDTO = Arrays.asList(userDTO);

        when(userService.getAllUsers()).thenReturn(list);
        when(mappingService.mapTo(list, UserDTO.class)).thenReturn(listDTO);

        Collection<UserDTO> found = userFacade.getAllUsers();

        verify(userService).getAllUsers();
        verify(mappingService).mapTo(list, UserDTO.class);

        Assert.assertEquals(listDTO, found);
    }

    @Test
    public void testGetByName() {
        List<User> list = Arrays.asList(user);
        List<UserDTO> listDTO = Arrays.asList(userDTO);
        String name = "name";

        when(userService.findUsersByName(name)).thenReturn(list);
        when(mappingService.mapTo(list, UserDTO.class)).thenReturn(listDTO);

        Collection<UserDTO> found = userFacade.getUsersByName(name);

        verify(userService).findUsersByName(name);
        verify(mappingService).mapTo(list, UserDTO.class);

        Assert.assertEquals(listDTO, found);
    }

    @Test
    public void testRegisterUser() {
        when(mappingService.mapTo(userDTO, User.class)).thenReturn(user);
        when(user.getId()).thenReturn(1L);

        userFacade.registerUser(userDTO, "password");

        verify(userService).registerUser(user, "password");
    }

    @Test
    public void testAuthenticate() {
        when(userService.findUserByEmail(userLoginDTO.getEmail())).thenReturn(user);
        when(userLoginDTO.getPassword()).thenReturn("password");
        userFacade.authenticateUser(userLoginDTO);

        verify(userService).authenticateUser(user, "password");
    }

    @Test
    public void testHasManagerRights() {
        when(userService.hasUserManagerRights(user)).thenReturn(true);
        when(mappingService.mapTo(userDTO, User.class)).thenReturn(user);

        Assert.assertTrue(userFacade.hasUserManagerRights(userDTO));
        verify(userService).hasUserManagerRights(user);
    }

    @Test
    public void testIsUserActiveFalse() {
        when(userService.isUserActive(user)).thenReturn(true);
        when(mappingService.mapTo(userDTO, User.class)).thenReturn(user);

        Assert.assertTrue(userFacade.isUserActive(userDTO));
        verify(userService).isUserActive(user);
    }

    @Test
    public void testChangeRightsLevel() {
        when(userService.findUserById(1L)).thenReturn(user);
        userFacade.changeRightsLevel(1L, RightsLevel.HERO);
        verify(userService).saveUser(user);
    }

    @Test
    public void testChangeUserStatus() {
        when(userService.findUserById(1L)).thenReturn(user);
        userFacade.changeUserStatus(1L, UserStatus.INACTIVE);
        verify(userService).saveUser(user);
    }

    @Test
    public void testChangeImage() {
        String mimeType = "image/jpg";
        byte [] image = new byte[256];

        when(userService.findUserById(changeUserImageDTO.getUserId())).thenReturn(user);
        when(changeUserImageDTO.getImage()).thenReturn(image);
        when(changeUserImageDTO.getImageMimeType()).thenReturn(mimeType);

        userFacade.changeUserImage(changeUserImageDTO);

        verify(userService).editUserImage(user, image,mimeType );
    }

}
