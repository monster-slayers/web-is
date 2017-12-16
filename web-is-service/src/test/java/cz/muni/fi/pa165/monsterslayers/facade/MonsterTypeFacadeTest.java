package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.monstertype.CreateMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.ModifyMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.MonsterTypeService;
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

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for MonsterTypeFacade class.
 *
 * @author David Kizivat
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-context.xml")
public class MonsterTypeFacadeTest {
    @Mock
    private MonsterTypeService service;

    @Autowired
    @InjectMocks
    private MonsterTypeFacade facade;

    @Mock
    private MappingService mappingService;

    private MonsterTypeDTO monsterTypeDto = new MonsterTypeDTO();

    private ModifyMonsterTypeDTO modifyMonsterTypeDto = new ModifyMonsterTypeDTO();

    private CreateMonsterTypeDTO createMonsterTypeDto = new CreateMonsterTypeDTO();

    private MonsterType monsterType = new MonsterType();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createMonsterTypeServiceCallTest() {
        when(mappingService.mapTo(createMonsterTypeDto, MonsterType.class)).thenReturn(monsterType);
        facade.createMonsterType(createMonsterTypeDto);
        verify(service).save(monsterType);
    }

    @Test
    public void deleteMonsterTypeServiceCallTest() {
        Long id = 13L;
        when(service.findById(id)).thenReturn(monsterType);
        facade.deleteMonsterType(id);
        verify(service).remove(monsterType);
    }

    @Test
    public void editMonsterTypeServiceCallTest() {
        Long id = 13L;
        when(service.findById(modifyMonsterTypeDto.getId())).thenReturn(monsterType);
        facade.editMonsterType(modifyMonsterTypeDto);
        verify(service).save(monsterType);
    }

    @Test
    public void getAllMonsterTypesServiceCallTest() {
        Collection<MonsterType> list = Collections.singletonList(monsterType);
        Collection<MonsterTypeDTO> dtoList = Collections.singletonList(monsterTypeDto);

        when(service.findAll()).thenReturn(list);
        when(mappingService.mapTo(list, MonsterTypeDTO.class)).thenReturn(dtoList);

        Iterable<MonsterTypeDTO> result = facade.getAllMonsterTypes();

        verify(service).findAll();
        verify(mappingService).mapTo(list, MonsterTypeDTO.class);

        Assert.assertEquals(dtoList, result);
    }

    @Test
    public void getMonsterTypeByNameServiceCallTest() {
        String name = "MonsterTypeName";

        when(service.findByName(name)).thenReturn(monsterType);
        when(mappingService.mapTo(monsterType, MonsterTypeDTO.class)).thenReturn(monsterTypeDto);

        MonsterTypeDTO result = facade.getMonsterTypeByName(name);

        verify(service).findByName(name);

        Assert.assertEquals(monsterTypeDto, result);
    }

    @Test
    public void getMonsterTypeByIdServiceCallTest() {
        Long id = 13L;

        when(service.findById(id)).thenReturn(monsterType);
        when(mappingService.mapTo(monsterType, MonsterTypeDTO.class)).thenReturn(monsterTypeDto);

        MonsterTypeDTO result = facade.getMonsterTypeById(id);

        verify(service).findById(id);

        Assert.assertEquals(monsterTypeDto, result);
    }
}
