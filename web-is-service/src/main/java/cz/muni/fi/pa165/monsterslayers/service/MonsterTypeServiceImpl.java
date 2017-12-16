package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.exception.MonsterSlayersException;
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
        try {
            return monsterTypeRepository.findOne(id);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find monster type with id " + id.toString() + ".",
                    e
            );
        }
    }

    @Override
    public Iterable<MonsterType> findAll() {
        try {
            return monsterTypeRepository.findAll();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find all monster types.",
                    e
            );
        }
    }

    @Override
    public Long save(MonsterType monsterType) {
        try {
            return monsterTypeRepository.save(monsterType).getId();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot save monster type.",
                    e
            );
        }
    }

    @Override
    public void remove(MonsterType monsterType) {
        try {
            monsterTypeRepository.delete(monsterType);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot remove monster type.",
                    e
            );
        }
    }

    @Override
    public MonsterType findByName(String name) {
        try {
            return monsterTypeRepository.findByName(name);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find monster type with name " + name + ".",
                    e
            );
        }
    }
}
