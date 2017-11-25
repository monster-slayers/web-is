package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer implementation for MonsterType.
 *
 * @author Ondrej Budai
 */
@Service
public class MonsterTypeServiceImpl implements MonsterTypeService {
    @Autowired
    private MonsterTypeRepository monsterTypeRepository;

    @Override
    public MonsterType findById(Long id) {
        return monsterTypeRepository.findOne(id);
    }

    @Override
    public Iterable<MonsterType> findAll() {
        return monsterTypeRepository.findAll();
    }

    @Override
    public Long create(MonsterType monsterType) {
        return monsterTypeRepository.save(monsterType).getId();
    }

    @Override
    public void remove(MonsterType monsterType) {
        monsterTypeRepository.delete(monsterType);
    }

    @Override
    public MonsterType findByName(String name) {
        return monsterTypeRepository.findByName(name);
    }
}
