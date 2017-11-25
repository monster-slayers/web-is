package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.CreateMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.ModifyMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;

/**
 * Facade layer interface for MonsterType.
 *
 * @author Ondrej Budai
 */
public interface MonsterTypeFacade {

    Long createMonsterType(CreateMonsterTypeDTO createMonsterTypeDTO);

    void deleteMonsterType(Long id);

    void editMonsterType(ModifyMonsterTypeDTO modifyMonsterTypeDTO);

    Iterable<MonsterTypeDTO> getAllMonsterTypes();

    MonsterTypeDTO getMonsterTypeByName(String name);

    MonsterTypeDTO getMonsterTypeById(Long id);
}
