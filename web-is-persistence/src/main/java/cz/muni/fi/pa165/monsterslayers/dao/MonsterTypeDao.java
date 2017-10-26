package cz.muni.fi.pa165.monsterslayers.dao;

import java.util.List;

//TODO: uncomment me after MonsterType entity is done
//import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;

/**
 * Monster type's data access object interface
 * @author Ondřej Budai
 */
public interface MonsterTypeDao {
    //TODO: delete me after MonsterType entity is done
    interface MonsterType{}
    public MonsterType findById(Long id);
    public void create(MonsterType monsterType);
    public void delete(MonsterType monsterType);
    public List<MonsterType> findAll();
    public MonsterType findByName(String name);
}
