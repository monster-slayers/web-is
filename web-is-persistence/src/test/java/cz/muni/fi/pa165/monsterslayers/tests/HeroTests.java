package cz.muni.fi.pa165.monsterslayers.tests;

import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.dao.UserRepository;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
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
import java.util.HashSet;

/**
 * Test class for Hero entity
 *
 * @author Maksym Tsuhui
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
@Transactional
public class HeroTests {
    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Hero hero;

    private String heroName = "Hero name";
    private PowerElement elementEarth = PowerElement.EARTH;
    private PowerElement elementPoison = PowerElement.POISON;

    @Before
    public void setup() {
        HashSet<PowerElement> elements = new HashSet<>();
        elements.add(elementEarth);
        elements.add(elementPoison);

        user = new User();
        user.setName("User name");
        user.setEmail("user@name.com");
        user.setPassword("userPassword");
        userRepository.save(user);

        hero = new Hero();
        hero.setUser(user);
        hero.setHeroName(heroName);
        hero.setElements(elements);
        heroRepository.save(hero);
    }

    @Test
    public void attributesTest() {
        Hero found = heroRepository.findOne(hero.getId());
        Assert.assertEquals(user, found.getUser());
        Assert.assertEquals(heroName, found.getHeroName());
        Assert.assertTrue(found.getElements().size() == 2);
    }


    @Test
    public void elementsTest() {
        Assert.assertTrue(hero.hasElement(elementEarth));
        Assert.assertFalse(hero.hasElement(PowerElement.GHOST));

        hero.removeElement(elementEarth);
        Assert.assertFalse(hero.hasElement(elementEarth));

        hero.addElement(PowerElement.GHOST);
        Assert.assertTrue(hero.hasElement(PowerElement.GHOST));
    }

    @Test(expected = DataAccessException.class)
    public void saveUniqueNameTest() {
        Hero hero1 = new Hero();
        hero1.setUser(user);
        hero1.setHeroName(heroName);
        heroRepository.save(hero1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullNameTest() {
        Hero hero1 = new Hero();
        hero1.setUser(user);
        heroRepository.save(hero1);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveNullUserTest() {
        Hero hero1 = new Hero();
        hero1.setHeroName("other hero name");
        heroRepository.save(hero1);
    }

    @Test
    public void equalityTest() {
        Assert.assertEquals(hero, hero);
        Assert.assertNotEquals(hero, null);

        Hero hero1 = new Hero();
        hero1.setUser(user);
        hero1.setHeroName(heroName);
        Assert.assertEquals(hero, hero1);

        hero1.setHeroName("other hero name");
        Assert.assertNotEquals(hero, hero1);
    }

    @Test
    public void hashCodeTest() {
        Hero hero1 = new Hero();
        hero1.setUser(user);
        hero1.setHeroName(heroName);
        Assert.assertEquals(hero, hero1);
        Assert.assertEquals(hero.hashCode(), hero1.hashCode());
    }
}
