package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.user.ChangeUserImageDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserLoginDTO;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.PasswordService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
    private PasswordService passwordService;

    private UserLoginDTO userLoginDTO = new UserLoginDTO("a", "b");

    private UserDTO userDTO = new UserDTO();

    private ChangeUserImageDTO changeUserImageDTO = new ChangeUserImageDTO();

    private User user = new User();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
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
        when(passwordService.createHash("password")).thenReturn("hash");

        userFacade.registerUser(userDTO, "password");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userService).registerUser(captor.capture());

        Assert.assertEquals(captor.getValue(), user);
        Assert.assertEquals(user.getPassword(), "hash");
    }

    @Test
    public void testAuthenticate() {
        UserLoginDTO login = new UserLoginDTO("info@example.com", "1234");
        User sampleUser = new User();
        sampleUser.setPassword("secret");

        when(userService.findUserByEmail(login.getEmail())).thenReturn(sampleUser);
        when(passwordService.checkHash(login.getPassword(), sampleUser.getPassword())).thenReturn(true);

        Assert.assertTrue(userFacade.authenticateUser(login));
    }

    @Test
    public void testAuthenticateBad() {
        UserLoginDTO login = new UserLoginDTO("info@example.com", "1234");
        User sampleUser = new User();
        sampleUser.setPassword("secret");

        when(userService.findUserByEmail(login.getEmail())).thenReturn(sampleUser);
        when(passwordService.checkHash(login.getPassword(), sampleUser.getPassword())).thenReturn(false);

        Assert.assertFalse(userFacade.authenticateUser(login));
    }

    @Test
    public void testHasManagerRights() {
        when(userService.hasManagerRights(user)).thenReturn(true);
        when(mappingService.mapTo(userDTO, User.class)).thenReturn(user);

        Assert.assertTrue(userFacade.hasManagerRights(userDTO));
        verify(userService).hasManagerRights(user);
    }

    @Test
    public void testHasHeroRights() {
        when(userService.hasHeroRights(user)).thenReturn(true);
        when(mappingService.mapTo(userDTO, User.class)).thenReturn(user);

        Assert.assertTrue(userFacade.hasHeroRights(userDTO));
        verify(userService).hasHeroRights(user);
    }

    @Test
    public void testChangeRightsLevel() {
        when(userService.findUserById(1L)).thenReturn(user);
        userFacade.changeRightsLevel(1L, RightsLevel.HERO);
        verify(userService).saveUser(user);
    }

    @Test
    public void testChangeImage() {
        String mimeType = "image/jpg";
        byte [] image = new byte[256];

        changeUserImageDTO.setImageMimeType(mimeType);
        changeUserImageDTO.setImage(image);

        when(userService.findUserById(changeUserImageDTO.getUserId())).thenReturn(user);

        userFacade.changeUserImage(changeUserImageDTO);

        verify(userService).editUserImage(user, image, mimeType);
    }

}
