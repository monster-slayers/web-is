package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        return heroRepository.findOne(id);
    }

    @Override
    public Collection<Hero> getAllHeroes() {
        return (Collection<Hero>) heroRepository.findAll();
    }

    @Override
    public void removeHero(Hero hero) {
        heroRepository.delete(hero);
    }

    @Override
    public void saveHero(Hero hero) {
        heroRepository.save(hero);
    }

    @Override
    public Hero findHeroByName(String heroName) {
        return heroRepository.findByHeroName(heroName);
    }

    private PowerElementsMatch countSuitabilityAgainstMonster(Hero hero, MonsterType monsterType) {
        int usefulElements = 0;
        for (PowerElement monsterWeakness : monsterType.getWeaknesses()) {
            if (hero.hasElement(monsterWeakness)) {
                usefulElements++;
            }
        }
        int uselessElements = hero.getElements().size() - usefulElements;
        return new PowerElementsMatch(usefulElements, uselessElements);
    }

    @Override
    public Hero findBestHeroForClientRequest(ClientRequest clientRequest) {
        Hero bestHero = null;
        PowerElementsMatch bestMatch = null;
        for (Hero hero : getAllHeroes()) {
            List<PowerElementsMatch> matches = new ArrayList<>();
            for (Map.Entry<MonsterType, Integer> entry : clientRequest.getKillList().entrySet()) {
                matches.add(
                        countSuitabilityAgainstMonster(hero, entry.getKey())
                                .multiplyByMonsterCount(entry.getValue())
                );
            }
            PowerElementsMatch match = PowerElementsMatch.sumMatches(matches);
            
            if (bestHero == null || match.isMoreSuitable(bestMatch)) {
                bestHero = hero;
                bestMatch = match;
            }
        }
        return bestHero;
    }
}
