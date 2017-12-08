package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
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

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for user service
 *
 * @author Ondrej Budai
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class HeroServiceTest {
    @Mock
    private HeroRepository heroRepository;

    @Autowired
    @InjectMocks
    private HeroService heroService;

    private boolean initialized = false;

    final private Hero hero1 = new Hero();
    final private Hero hero2 = new Hero();
    final private Hero hero3 = new Hero();
    final private Hero hero4 = new Hero();

    final private List<Hero> sampleHeroes = new ArrayList<>();

    public HeroServiceTest(){
        hero1.setId(1L);
        hero1.setHeroName("Maruna");
        hero1.addElement(PowerElement.EARTH);
        hero1.addElement(PowerElement.FIRE);
        hero1.addElement(PowerElement.POISON);
        hero2.setId(2L);
        hero1.setHeroName("Lucina");
        hero3.setId(3L);
        hero1.setHeroName("Petrunie");
        hero4.setId(4L);
        hero1.setHeroName("Karla");

        sampleHeroes.add(hero1);
        sampleHeroes.add(hero2);
        sampleHeroes.add(hero3);
        sampleHeroes.add(hero4);
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
    public void testFindHeroById(){
        when(heroRepository.findOne(hero1.getId())).thenReturn(hero1);

        Hero actualHero = heroService.findHeroById(hero1.getId());

        Assert.assertEquals(hero1, actualHero);
    }

    @Test
    public void testFindHeroByIdNoHero(){
        when(heroRepository.findOne(666L)).thenReturn(null);

        Assert.assertNull(heroService.findHeroById(666L));
    }

    @Test
    public void testGetAllHeroes(){
        when(heroRepository.findAll()).thenReturn(sampleHeroes);

        Iterable<Hero> actualHeroes = heroService.getAllHeroes();

        Assert.assertThat(actualHeroes, IsIterableContainingInAnyOrder.containsInAnyOrder(sampleHeroes.toArray()));
    }

    @Test
    public void testGetAllHeroesNoHeroes(){
        List<Hero> emptyHeroList = new ArrayList<>();
        when(heroRepository.findAll()).thenReturn(emptyHeroList);

        Iterable<Hero> actualHeroes = heroService.getAllHeroes();

        Assert.assertFalse(actualHeroes.iterator().hasNext());
    }

    @Test
    public void testRemoveHero(){
        heroService.removeHero(hero1);

        // hero service can call both delete(Hero) or delete(Long)
        // therefore we need to check both

        ArgumentCaptor<Hero> heroCaptor = ArgumentCaptor.forClass(Hero.class);
        verify(heroRepository, atLeast(0)).delete(heroCaptor.capture());

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(heroRepository, atLeast(0)).delete(idCaptor.capture());

        Assert.assertTrue(
                (!heroCaptor.getAllValues().isEmpty() && heroCaptor.getValue().equals(hero1)) ||
                        (!idCaptor.getAllValues().isEmpty() && idCaptor.getValue().equals(hero1.getId()))
        );
    }

    @Test
    public void testSaveHero(){
        when(heroRepository.save(hero1)).thenReturn(hero1);

        heroService.saveHero(hero1);

        verify(heroRepository).save(hero1);
    }

    @Test
    public void testFindHeroByName(){
        when(heroRepository.findByHeroName(hero1.getHeroName())).thenReturn(hero1);

        Hero actualHero = heroService.findHeroByName(hero1.getHeroName());

        Assert.assertEquals(actualHero, hero1);
    }

    @Test
    public void testFindHeroByNameNoHero(){
        when(heroRepository.findByHeroName("Lumyse")).thenReturn(null);

        Assert.assertNull(heroService.findHeroByName("Lumyse"));

        verify(heroRepository).findByHeroName("Lumyse");
    }

    @Test
    public void testCountHeroSuitability(){
        MonsterType monsterType = new MonsterType();
        monsterType.addWeakness(PowerElement.GHOST);
        monsterType.addWeakness(PowerElement.EARTH);
        monsterType.addWeakness(PowerElement.FIRE);
        monsterType.addWeakness(PowerElement.POISON);

        Assert.assertEquals(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType), new PowerElementsMatch(3, 0));

        monsterType.removeWeakness(PowerElement.GHOST);
        Assert.assertEquals(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType), new PowerElementsMatch(3, 0));

        monsterType.removeWeakness(PowerElement.POISON);
        Assert.assertEquals(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType), new PowerElementsMatch(2, 1));

        monsterType.removeWeakness(PowerElement.EARTH);
        Assert.assertEquals(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType), new PowerElementsMatch(1, 2));

        monsterType.removeWeakness(PowerElement.FIRE);
        Assert.assertEquals(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType), new PowerElementsMatch(0, 3));

        monsterType.addWeakness(PowerElement.EARTH);
        Assert.assertEquals(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType), new PowerElementsMatch(1, 2));
    }
}
