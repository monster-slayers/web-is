package cz.muni.fi.pa165.monsterslayers.tests.dataaccess;

import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

/**
 * This class implements integration tests for data access functionality for MonsterType entity
 * using MonsterTypeRepository.
 *
 * @author David Kizivat
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
@Transactional
public class MonsterTypeRepositoryTests {
    @Autowired
    private MonsterTypeRepository repository;

    private MonsterType monsterType1;
    private MonsterType monsterType2;

    @Before
    public void setup() {
        monsterType1 = new MonsterType("Monster1");
        monsterType2 = new MonsterType("Monster2");
    }

    @Test
    public void saveMonsterTypeTest() {
        Assert.assertEquals(monsterType1, repository.save(monsterType1));
    }

    @Test
    public void saveMultipleMonsterTypesTest() {
         Assert.assertEquals(
                 Arrays.asList(monsterType1, monsterType2),
                 repository.save(Arrays.asList(monsterType1, monsterType2)));
    }

    @Test
    public void existsMonsterTypeTest() {
        MonsterType saved = repository.save(monsterType1);
        Assert.assertTrue(repository.exists(saved.getId()));
    }

    @Test
    public void findOneMonsterTypeTest() {
        MonsterType saved = repository.save(monsterType1);
        Assert.assertEquals(monsterType1, repository.findOne(saved.getId()));
    }

    @Test
    public void findAllMonsterTypesTest() {
        repository.save(Arrays.asList(monsterType1, monsterType2));
        Assert.assertEquals(
                Arrays.asList(monsterType1, monsterType2),
                repository.findAll());
    }

    @Test
    public void deleteMonsterTypeByIdTest() {
        MonsterType saved = repository.save(monsterType1);
        repository.delete(saved.getId());
        Assert.assertNull(repository.findOne(saved.getId()));
    }

    @Test
    public void deleteMonsterTypeTest() {
        MonsterType saved = repository.save(monsterType1);
        repository.delete(monsterType1);
        Assert.assertNull(repository.findOne(saved.getId()));
    }

    @Test
    public void deleteMonsterTypeByIterableTest() {
        MonsterType saved1 = repository.save(monsterType1);
        MonsterType saved2 = repository.save(monsterType1);
        repository.delete(Arrays.asList(monsterType1, monsterType2));
        Assert.assertNull(repository.findOne(saved1.getId()));
        Assert.assertNull(repository.findOne(saved2.getId()));
    }

    @Test
    public void deleteAllMonsterTypesTest() {
        repository.save(monsterType1);
        repository.save(monsterType1);
        repository.deleteAll();
        Assert.assertEquals(Collections.emptyList(), repository.findAll());
    }

    @Test
    public void countMonsterTypesTest() {
        repository.save(monsterType1);
        Assert.assertEquals(1, repository.count());
        repository.save(monsterType2);
        Assert.assertEquals(2, repository.count());
        repository.delete(monsterType1);
        Assert.assertEquals(1, repository.count());
        repository.deleteAll();
        Assert.assertEquals(0, repository.count());
    }

    @Test
    public void findMonsterTypeByNameTest() {
        MonsterType saved = repository.save(monsterType1);
        Assert.assertEquals(monsterType1, repository.findByName(saved.getName()));
    }

}
