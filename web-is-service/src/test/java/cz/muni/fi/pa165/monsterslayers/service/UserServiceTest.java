package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.UserRepository;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for user service
 *
 * @author Ondrej Budai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserService userService;

    private boolean initialized = false;

    private final List<User> sampleUsers = new ArrayList<>();
    private final User user1 = new User();
    private final User user2 = new User();
    private final User user3 = new User();
    private final User user4 = new User();

    private final List<User> emptyUsers = new ArrayList<>();

    public UserServiceTest(){
        user1.setId(1L);
        user1.setName("Pepa");
        user1.setEmail("pepa@pepa.com");
        // salt: "salt", password: "holubyvnose"
        user1.setPassword("1000:73616c74:6313553dbe97e1bfd2061832a8315e0c449126f3356881bdb57a94db5cb8a1e4");
        user1.setRightsLevel(RightsLevel.MANAGER);

        user2.setName("Jarda");
        user2.setRightsLevel(RightsLevel.HERO);

        user3.setName("Pepa");
        user3.setRightsLevel(RightsLevel.CLIENT);

        user4.setName("Karel");

        sampleUsers.add(user1);
        sampleUsers.add(user2);
        sampleUsers.add(user3);
        sampleUsers.add(user4);
    }

    @Before
    public void setup() {
        // this is not optimal, but junit requires from method
        // annotated with @Before to be static
        // possibly better solution is to use testng instead...
        if (initialized) {
            return;
        }
        MockitoAnnotations.initMocks(this);

        initialized = true;
    }

    @Test
    public void testFindUserById(){
        when(userRepository.findOne(user1.getId())).thenReturn(user1);

        User actualUser = userService.findUserById(1L);
        Assert.assertEquals(actualUser, user1);
    }

    @Test
    public void testFindUserByIdNonExisting(){
        Long expectedId = 1L;
        when(userRepository.findOne(expectedId)).thenReturn(null);

        User actualUser = userService.findUserById(expectedId);
        Assert.assertNull(actualUser);
    }

    @Test
    public void testGetAllUsers(){
        when(userRepository.findAll()).thenReturn(sampleUsers);

        Iterable<User> actualUsers = userService.getAllUsers();

        Assert.assertThat(actualUsers, IsIterableContainingInAnyOrder.containsInAnyOrder(sampleUsers.toArray()));
    }

    @Test
    public void testGetAllUsersNoUsers(){

        when(userRepository.findAll()).thenReturn(emptyUsers);

        Iterable<User> actualUsers = userService.getAllUsers();

        Assert.assertFalse(actualUsers.iterator().hasNext());
    }

    @Test
    public void testFindUsersByName(){
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user3);

        when(userRepository.findByName("Pepa")).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findUsersByName("Pepa");
        Assert.assertThat(actualUsers, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedUsers.toArray()));
    }

    @Test
    public void testFindUsersByNameNoUsers(){
        when(userRepository.findByName("Jaryn")).thenReturn(emptyUsers);

        List<User> actualUsers = userService.findUsersByName("Jaryn");

        Assert.assertEquals(actualUsers.size(), 0);
    }

    @Test
    public void testFindUserByEmail(){
        when(userService.findUserByEmail(user1.getEmail())).thenReturn(user1);

        User expectedUser = userService.findUserByEmail(user1.getEmail());

        Assert.assertEquals(expectedUser, user1);
    }

    @Test
    public void testFindUserByEmailNoUser(){
        String email = "noemail@example.com";
        when(userService.findUserByEmail(email)).thenReturn(null);

        User expectedUser = userService.findUserByEmail(email);

        Assert.assertNull(expectedUser);
    }

    @Test
    public void testRegisterUser(){
        userService.registerUser(user1);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User registeredUser = userCaptor.getValue();

        Assert.assertEquals(registeredUser, user1);
    }

    @Test
    public void testHasUserManagerRights(){
        when(userRepository.findOne(user1.getId())).thenReturn(user1);

        Assert.assertTrue(userService.hasManagerRights(user1));
    }

    @Test
    public void testHasUserManagerRightsNoManager(){
        when(userRepository.findOne(user2.getId())).thenReturn(user2);
        when(userRepository.findOne(user3.getId())).thenReturn(user3);

        Assert.assertFalse(userService.hasManagerRights(user2));
        Assert.assertFalse(userService.hasManagerRights(user3));
    }
    
    @Test
    public void testHasHeroRights(){
        when(userRepository.findOne(user1.getId())).thenReturn(user1);
        when(userRepository.findOne(user2.getId())).thenReturn(user2);
        
        Assert.assertTrue(userService.hasHeroRights(user1));
        Assert.assertTrue(userService.hasHeroRights(user2));
    }
    
    @Test
    public void testHasHeroRightsJustClient(){
        when(userRepository.findOne(user3.getId())).thenReturn(user3);
        
        Assert.assertFalse(userService.hasHeroRights(user3));
    }

    @Test
    public void editUserImage(){
        byte[] image = new byte[256];
        String mimeType = "image/png";

        userService.editUserImage(user1, image, mimeType);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());

        User capturedUser = captor.getValue();

        Assert.assertEquals(capturedUser.getImage(), image);
        Assert.assertEquals(capturedUser.getImageMimeType(), mimeType);
    }

}
