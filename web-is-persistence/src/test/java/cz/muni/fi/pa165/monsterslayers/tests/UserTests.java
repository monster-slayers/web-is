package cz.muni.fi.pa165.monsterslayers.tests;

import cz.muni.fi.pa165.monsterslayers.dao.UserRepository;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.enums.UserStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

/**
 * Test class for User entity
 *
 * @author Maksym Tsuhui
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
@Transactional
public class UserTests {
    @Autowired
    private UserRepository userRepository;

    private User user;

    private String name = "user name";
    private String email = "user@name.com";
    private String password = "userPassword";
    private UserStatus userStatus = UserStatus.ACTIVE;
    private RightsLevel rightsLevel = RightsLevel.CLIENT;
    private String imageMimeType = "image type";
    private byte[] image = new byte[1];

    @Before
    public void setup() {
        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setStatus(userStatus);
        user.setRightsLevel(rightsLevel);
        user.setImage(image);
        user.setImageMimeType(imageMimeType);
        userRepository.save(user);
    }

    @Test
    public void attributesTest() {
        User found = userRepository.findOne(user.getId());
        Assert.assertEquals(name, found.getName());
        Assert.assertEquals(email, found.getEmail());
        Assert.assertEquals(password, found.getPassword());
        Assert.assertEquals(userStatus, found.getStatus());
        Assert.assertEquals(rightsLevel, found.getRightsLevel());
        Assert.assertEquals(image, found.getImage());
        Assert.assertEquals(imageMimeType, found.getImageMimeType());
    }

    @Test(expected = DataAccessException.class)
    public void saveUniqueEmailTest() {
        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setPassword(password);
        userRepository.save(user1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullNameTest() {
        User user1 = new User();
        user1.setEmail("email@other.cz");
        user1.setPassword(password);
        userRepository.save(user1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullEmailTest() {
        User user1 = new User();
        user.setName(name);
        user1.setPassword(password);
        userRepository.save(user1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullPasswordTest() {
        User user1 = new User();
        user.setName(name);
        user1.setEmail("email@other.cz");
        userRepository.save(user1);
    }

    @Test
    public void equalityTest() {
        Assert.assertEquals(user, user);
        Assert.assertNotEquals(user, null);

        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setPassword(password);
        Assert.assertEquals(user, user1);

        user1.setEmail("other@email.cz");
        Assert.assertNotEquals(user, user1);
    }

    @Test
    public void hashCodeTest() {
        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setPassword(password);
        Assert.assertEquals(user.hashCode(), user1.hashCode());
    }
}
