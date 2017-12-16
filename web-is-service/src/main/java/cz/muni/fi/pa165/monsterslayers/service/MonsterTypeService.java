package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.springframework.stereotype.Service;

/**
 * Service layer interface for MonsterType.
 *
 * @author Ondrej Budai
 */
@Service
public interface MonsterTypeService {
    /**
     * Finds monster type by given id
     *
     * @param id given id
     * @return found monster type entity
     */
    MonsterType findById(Long id);

    /**
     * Finds all monster types
     *
     * @return Iterable collection of monster types
     */
    Iterable<MonsterType> findAll();

    /**
     * Saves monster type
     *
     * @param monsterType monster type entity that will be created
     * @return id of created monster type
     */
    Long save(MonsterType monsterType);

    /**
     * Removes monster type
     *
     * @param monsterType monster type entity that will be removed
     */
    void remove(MonsterType monsterType);

    /**
     * Finds monster type with specific name
     *
     * @param name monster type name
     * @return found monster type entity
     */
    MonsterType findByName(String name);
}
