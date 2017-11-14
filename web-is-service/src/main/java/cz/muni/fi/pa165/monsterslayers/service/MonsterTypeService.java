package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.springframework.stereotype.Service;

@Service
public interface MonsterTypeService {
    MonsterType findById(Long id);
    Iterable<MonsterType> findAll();
    void create(MonsterType monsterType);
    void remove(MonsterType monsterType);
}
