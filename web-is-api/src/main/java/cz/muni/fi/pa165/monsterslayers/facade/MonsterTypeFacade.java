package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.monstertype.CreateMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.ModifyMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;

/**
 * Facade layer interface for MonsterType.
 *
 * @author Ondrej Budai
 */
public interface MonsterTypeFacade {
    /**
     * Creates new monster type according to special DTO
     * 
     * @param createMonsterTypeDTO DTO for creating monster type
     * @return id of created monster type
     */
    Long createMonsterType(CreateMonsterTypeDTO createMonsterTypeDTO);

    /**
     * Deletes monster type with specific id
     * 
     * @param id id of monster type that will be deleted
     */
    void deleteMonsterType(Long id);

    /**
     * Edits monster type according to special DTO
     * 
     * @param modifyMonsterTypeDTO DTO for modification of monster type
     */
    void editMonsterType(ModifyMonsterTypeDTO modifyMonsterTypeDTO);

    /**
     * Gets all monster type as DTOs
     * 
     * @return Iterable collection of DTOs 
     */
    Iterable<MonsterTypeDTO> getAllMonsterTypes();

    /**
     * Gets monster type with specific name as DTO
     * 
     * @param name specific name
     * @return single DTO
     */
    MonsterTypeDTO getMonsterTypeByName(String name);

    /**
     * Gets monster type with specific id as DTO
     * 
     * @param id specific id
     * @return single DTO
     */
    MonsterTypeDTO getMonsterTypeById(Long id);
}
