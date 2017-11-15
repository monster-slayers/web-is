package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.HeroRepository;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomáš Richter
 */
@Service
public class HeroServiceImpl implements HeroService {
    
    @Autowired
    private HeroRepository heroRepository;

    @Override
    public Hero findById(Long id) {
        return heroRepository.findOne(id);
    }

    @Override
    public Iterable<Hero> findAll() {
        return heroRepository.findAll();
    }

    @Override
    public void createOrUpdateIfExists(Hero hero) {
        heroRepository.save(hero);
    }

    @Override
    public void remove(Hero hero) {
        heroRepository.delete(hero);
    }

    @Override
    public Hero findByHeroName(String heroName) {
        return heroRepository.findByHeroName(heroName);
    }
}
