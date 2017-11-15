package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.User;
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
    public Iterable<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }

    @Override
    public void removeHero(Hero hero) {
        heroRepository.delete(hero);
    }

    @Override
    public Hero findHeroByName(String heroName) {
        return heroRepository.findByHeroName(heroName);
    }

    @Override
    public void createHeroOfUser(User user, Hero hero) {
        hero.setUser(user);
        heroRepository.save(hero);
    }
}
