package cz.muni.fi.pa165.monsterslayers.service;

import com.google.common.collect.Iterables;
import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class SampleTest {
    @Mock
    private MonsterTypeRepository monsterTypeRepository;

    @Autowired
    @InjectMocks
    private MonsterTypeService monsterTypeService;

    private boolean initialized = false;

    @Before
    public void setup(){
        // this is not optimal, but junit requires from method
        // annotated with @Before to be static
        // possibly better solution is to use testng instead...
        if(initialized){
            return;
        }
        MockitoAnnotations.initMocks(this);

        initialized = true;
    }

    @Test
    public void sampleTest(){
        Long expectedId = 42L;
        MonsterType mockMonsterType = new MonsterType();
        mockMonsterType.setId(expectedId);

        when(monsterTypeRepository.save(any(MonsterType.class))).thenReturn(mockMonsterType);
        monsterTypeService.create(new MonsterType());

        List<MonsterType> mockList = new ArrayList<>();
        mockList.add(mockMonsterType);
        when(monsterTypeRepository.findAll()).thenReturn(mockList);

        Assert.assertEquals(1, Iterables.size(monsterTypeService.findAll()));
        Assert.assertEquals(expectedId, monsterTypeService.findAll().iterator().next().getId());
    }

    


}
