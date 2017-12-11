package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.ModifyHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for hero facade tests
 *
 * @author Maksym Tsuhui
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class HeroFacadeTest {

    @Mock
    private HeroService heroService;

    @Autowired
    @InjectMocks
    private HeroFacade heroFacade;

    @Mock
    private MappingService mappingService;

    @Mock
    private CreateHeroDTO createHeroDTO;

    @Mock
    private HeroDTO heroDTO;

    @Mock
    private ModifyHeroDTO modifyHeroDTO;

    @Mock
    private Hero hero;
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
    public void testGetHeroById() {
        Long id = 1L;

        when(heroService.findHeroById(id)).thenReturn(hero);
        when(mappingService.mapTo(hero, HeroDTO.class)).thenReturn(heroDTO);

        HeroDTO found = heroFacade.getHeroById(id);

        verify(heroService).findHeroById(id);
        verify(mappingService).mapTo(hero, HeroDTO.class);
        Assert.assertEquals(heroDTO,found);
    }

    @Test
    public void testGetByName() {
        String name = "name";
        when(heroService.findHeroByName(name)).thenReturn(hero);
        when(mappingService.mapTo(hero, HeroDTO.class)).thenReturn(heroDTO);

        HeroDTO found = heroFacade.getHeroByName(name);

        verify(heroService).findHeroByName(name);
        verify(mappingService).mapTo(hero, HeroDTO.class);
        Assert.assertEquals(heroDTO,found);
    }

    @Test
    public void testGetAll() {
        Collection<Hero> list = Arrays.asList(hero);
        Collection<HeroDTO> listDTO = Arrays.asList(heroDTO);

        when(heroService.getAllHeroes()).thenReturn(list);
        when(mappingService.mapTo(list, HeroDTO.class)).thenReturn(listDTO);

        Collection<HeroDTO> found = heroFacade.getAllHeroes();

        verify(heroService).getAllHeroes();
        verify(mappingService).mapTo(list, HeroDTO.class);

        Assert.assertEquals(listDTO, found);
    }

    @Test
    public void testRemove() {
        when(mappingService.mapTo(heroDTO, Hero.class)).thenReturn(hero);

        heroFacade.removeHero(heroDTO);

        verify(heroService).removeHero(hero);
    }

    @Test
    public void testCreate() {
        when(mappingService.mapTo(createHeroDTO, Hero.class)).thenReturn(hero);

        heroFacade.createHero(createHeroDTO);

        verify(heroService).saveHero(hero);
    }

    @Test
    public void testEdit() {
        when(heroService.findHeroById(modifyHeroDTO.getHeroId())).thenReturn(hero);

        heroFacade.editHero(modifyHeroDTO);

        verify(heroService).saveHero(hero);
    }

    @Test
    public void testChangeUserStatus() {
        when(heroService.findHeroById(1L)).thenReturn(hero);
        heroFacade.changeStatus(1L, HeroStatus.INACTIVE);
        verify(heroService).saveHero(hero);
    }

}
