package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for MonsterTypeService
 *
 * @author Tsuhui Maksym
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class MonsterTypeServiceTest {
    @Mock
    private MonsterTypeRepository monsterTypeRepository;

    @Autowired
    @InjectMocks
    private MonsterTypeService monsterTypeService;

    private MonsterType monsterType1;
    private MonsterType monsterType2;
    private Collection<MonsterType> monsterTypes;

    @Before
    public void setup() {
        monsterType1 = new MonsterType("Zombie");
        monsterType1.setId(1L);
        monsterType1.setFood("food for zombie");
        monsterType1.addWeakness(PowerElement.FIRE);
        monsterType1.addWeakness(PowerElement.HOLY);

        monsterType2 = new MonsterType("Skeleton");
        monsterType2.setId(2L);
        monsterType2.setFood("bones");
        monsterType2.addWeakness(PowerElement.WATER);

        monsterTypes = new HashSet<>();
        monsterTypes.add(monsterType1);
        monsterTypes.add(monsterType2);
    }


    @Test
    public void testFindById(){
        when(monsterTypeRepository.findOne(monsterType1.getId())).thenReturn(monsterType1);

        MonsterType actualMonsterType = monsterTypeService.findById(monsterType1.getId());

        Assert.assertEquals(monsterType1, actualMonsterType);
    }

    @Test
    public void testFindByIdNotExisting(){
        when(monsterTypeRepository.findOne(monsterType1.getId())).thenReturn(null);

        MonsterType actualMonsterType = monsterTypeService.findById(monsterType1.getId());

        Assert.assertNull(actualMonsterType);
    }

    @Test
    public void testFindByName(){
        when(monsterTypeRepository.findByName("Zombie")).thenReturn(monsterType1);

        MonsterType actualMonsterType = monsterTypeService.findByName("Zombie");

        Assert.assertEquals(monsterType1, actualMonsterType);
    }

    @Test
    public void testFindByNameNotExisting(){
        when(monsterTypeRepository.findByName("Zombie")).thenReturn(null);

        MonsterType actualMonsterType = monsterTypeService.findByName("Zombie");

        Assert.assertNull(actualMonsterType);
    }

    @Test
    public void testFindAll(){
        when(monsterTypeRepository.findAll()).thenReturn(monsterTypes);

        Iterable<MonsterType> actualMonsterTypes = monsterTypeService.findAll();

        Assert.assertEquals(monsterTypes, actualMonsterTypes);
    }

    @Test
    public void testCreate(){
        when(monsterTypeRepository.save(monsterType1)).thenReturn(monsterType1);

        Long id = monsterTypeService.create(monsterType1);

        verify(monsterTypeRepository).save(monsterType1);
        Assert.assertEquals(id, monsterType1.getId());
    }

    @Test
    public void testRemove(){
        monsterTypeService.remove(monsterType1);

        verify(monsterTypeRepository).delete(monsterType1);
    }

    @Test
    public void testUpdate(){
        monsterTypeService.update(monsterType1);

        verify(monsterTypeRepository).save(monsterType1);
    }





}