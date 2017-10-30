package cz.muni.fi.pa165.monsterslayers.tests;

import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.entities.enums.Elements;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;

@ContextConfiguration(locations = "/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MonsterTypeTest {
    @Autowired
    private MonsterTypeRepository repository;

    private MonsterType monsterType;
    private Elements fire = Elements.FIRE;
    private Elements water = Elements.WATER;
    private Elements wind = Elements.WIND;

    private static final String monsterName = "Monster";

    @Before
    public void setup() {
        monsterType = new MonsterType(monsterName);
        monsterType.addWeakness(water);
        monsterType.addWeakness(fire);
        monsterType.setFood("lasagna");
        repository.save(monsterType);
    }

    @Test
    public void setGetId() {
        Long newId = 123456L;
        monsterType.setId(newId);
        Assert.assertEquals(newId, monsterType.getId());
    }

    @Test
    public void setGetName() {
        String newName = "NewName";
        monsterType.setName(newName);
        Assert.assertEquals(newName, monsterType.getName());
    }

    @Test
    public void setGetFood() {
        String newFood = "NewFood";
        monsterType.setFood(newFood);
        Assert.assertEquals(newFood, monsterType.getFood());
    }

    @Test
    public void setGetWeaknessesTest() {
        monsterType.setWeaknesses(Arrays.asList(wind, fire));
        Assert.assertEquals(Arrays.asList(wind, fire), monsterType.getWeaknesses());
    }

    @Test
    public void addWeaknessTest() {
        Assert.assertEquals(Arrays.asList(fire, water), monsterType.getWeaknesses());
        monsterType.addWeakness(wind);
        Assert.assertEquals(Arrays.asList(fire, water, wind), monsterType.getWeaknesses());
    }

    @Test
    public void removeWeaknessTest() {
        Assert.assertTrue(monsterType.removeWeakness(water));
        Assert.assertEquals(Collections.singletonList(fire), monsterType.getWeaknesses());
    }

    @Test
    public void hasWeaknessTest() {
        Assert.assertTrue(monsterType.hasWeakness(fire));
        Assert.assertTrue(monsterType.hasWeakness(water));
        Assert.assertFalse(monsterType.hasWeakness(wind));
    }

    @Test(expected = ConstraintViolationException.class)
    public void nameCannotBeNullTest() {
        MonsterType m = new MonsterType();
        repository.save(m);
    }

    @Test
    public void weaknessesPersistenceTest() {
        MonsterType saved = repository.save(monsterType);
        Assert.assertEquals(monsterType, repository.findOne(saved.getId()));
    }

    @Test
    public void equalityTest() {
        MonsterType m = new MonsterType(monsterName);
        Assert.assertTrue(monsterType.equals(m));
        Assert.assertEquals(monsterType, monsterType);

        m.setName("Monster2");
        Assert.assertFalse(monsterType.equals(m));
        Assert.assertNotEquals(monsterType, new ClientRequest());
    }

    @Test
    public void hashCodeTest() {
        MonsterType m = new MonsterType(monsterName);
        Assert.assertEquals(m.hashCode(), monsterType.hashCode());
    }

}
