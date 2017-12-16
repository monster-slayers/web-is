package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.exception.MonsterSlayersException;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of hero service interface
 *
 * @author Tomáš Richter
 */
@Service
public class HeroServiceImpl implements HeroService {

    @Autowired
    private HeroRepository heroRepository;

    @Override
    public Hero findHeroById(Long id) {
        try {
            return heroRepository.findOne(id);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find hero with ID" + id.toString() + ".",
                    e
            );
        }
    }

    @Override
    public Iterable<Hero> getAllHeroes() {
        try {
            return heroRepository.findAll();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot retrieve heroes.",
                    e
            );
        }
    }

    @Override
    public void removeHero(Hero hero) {
        try {
            heroRepository.delete(hero);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot delete hero.",
                    e
            );
        }
    }

    @Override
    public Long saveHero(Hero hero) {
        try {
            return heroRepository.save(hero).getId();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot save hero.",
                    e
            );
        }
    }

    @Override
    public Hero findHeroByName(String heroName) {
        try {
            return heroRepository.findByHeroName(heroName);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find hero with name " + heroName + ".",
                    e
            );
        }
    }

    @Override
    public PowerElementsMatch countHeroSuitabilityAgainstMonsterType(Hero hero, MonsterType monsterType) {
        int usefulElements = 0;
        for (PowerElement monsterWeakness : monsterType.getWeaknesses()) {
            if (hero.hasElement(monsterWeakness)) {
                usefulElements++;
            }
        }
        int uselessElements = hero.getElements().size() - usefulElements;
        return new PowerElementsMatch(usefulElements, uselessElements);
    }
}
