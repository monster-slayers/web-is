package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.springframework.data.repository.CrudRepository;

/**
 * MonsterType's repository interface extending spring CrudRepository
 *
 * @author Ondřej Budai
 */
public interface MonsterTypeRepository extends CrudRepository<MonsterType, Long> {
    /**
     * Find MonsterType by name.
     *
     * @param name name of MonsterType to find
     * @return instance of MonsterType with the provided name
     */
    MonsterType findByName(String name);
}
