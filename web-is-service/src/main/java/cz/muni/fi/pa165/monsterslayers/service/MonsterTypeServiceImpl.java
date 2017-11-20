package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void create(MonsterType monsterType) {
        monsterTypeRepository.save(monsterType);
    }

    @Override
    public void remove(MonsterType monsterType) {
        monsterTypeRepository.delete(monsterType);
    }
}
