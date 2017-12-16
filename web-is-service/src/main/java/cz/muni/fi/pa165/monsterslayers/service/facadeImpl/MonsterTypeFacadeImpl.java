package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.monstertype.CreateMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.ModifyMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.facade.MonsterTypeFacade;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.MonsterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Facade layer implementation for MonsterType.
 *
 * @author Ondrej Budai
 */
@Service
@Transactional
public class MonsterTypeFacadeImpl implements MonsterTypeFacade {

    @Autowired
    private MonsterTypeService monsterTypeService;

    @Autowired
    private MappingService mappingService;

    @Override
    public Long createMonsterType(CreateMonsterTypeDTO createMonsterTypeDTO) {
        MonsterType monsterType = mappingService.mapTo(createMonsterTypeDTO, MonsterType.class);

        monsterType.addWeakness(createMonsterTypeDTO.getWeakness());

        return monsterTypeService.create(monsterType);
    }

    @Override
    public void deleteMonsterType(Long id) {
        monsterTypeService.remove(monsterTypeService.findById(id));
    }

    @Override
    public void editMonsterType(ModifyMonsterTypeDTO modifyMonsterTypeDTO) {
        MonsterType monsterType = monsterTypeService.findById(modifyMonsterTypeDTO.getId());
        if(modifyMonsterTypeDTO.getName() != null){
            monsterType.setName(modifyMonsterTypeDTO.getName());
        }
        if(modifyMonsterTypeDTO.getFood() != null){
            monsterType.setFood(modifyMonsterTypeDTO.getFood());
        }
        if (modifyMonsterTypeDTO.getNewWeaknesses() != null) {
            monsterType.setWeaknesses(modifyMonsterTypeDTO.getNewWeaknesses());
        }
        monsterTypeService.update(monsterType);
    }

    @Override
    public Collection<MonsterTypeDTO> getAllMonsterTypes() {
        return mappingService.mapTo(monsterTypeService.findAll(), MonsterTypeDTO.class);
    }

    @Override
    public MonsterTypeDTO getMonsterTypeByName(String name) {
        return mappingService.mapTo(monsterTypeService.findByName(name), MonsterTypeDTO.class);
    }

    @Override
    public MonsterTypeDTO getMonsterTypeById(Long id) {
        return mappingService.mapTo(monsterTypeService.findById(id), MonsterTypeDTO.class);
    }
}
