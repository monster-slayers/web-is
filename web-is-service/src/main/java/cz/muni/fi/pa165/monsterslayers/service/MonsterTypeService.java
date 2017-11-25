package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;

/**
 * Service layer interface for MonsterType.
 *
 * @author Ondrej Budai
 */
public interface MonsterTypeService {
    MonsterType findById(Long id);
    Iterable<MonsterType> findAll();
    Long create(MonsterType monsterType);
    void remove(MonsterType monsterType);
    MonsterType findByName(String name);
}
