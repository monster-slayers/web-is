package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.monstertype.CreateMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.ModifyMonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.dto.monstertype.MonsterTypeDTO;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.facade.MonsterTypeFacade;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller for monster type
 *
 * @author Maksym Tsuhui
 */
@RestController
@RequestMapping("/monster-type")
public class MonsterTypeController {

    @Autowired
    private MonsterTypeFacade monsterTypeFacade;

    @GetMapping
    Iterable<MonsterTypeDTO> getAllMonsterTypes() {
        return monsterTypeFacade.getAllMonsterTypes();
    }

    @PostMapping(value = "/create/{name}/{food}/{weakness}")
    private void createMonster(@PathVariable("name") String name, @PathVariable("food") String food, @PathVariable("weakness") PowerElement weakness) {
        CreateMonsterTypeDTO createDTO = new CreateMonsterTypeDTO();
        createDTO.setName(name);
        createDTO.setFood(food);
        createDTO.setWeakness(weakness);
        monsterTypeFacade.createMonsterType(createDTO);
    }

    @PutMapping(value = "/modify/{id}/{name}/{food}/{weaknesses}")
    private void modifyMonster(@PathVariable("id") Long id, @PathVariable("name") String name, @PathVariable("food") String food, @PathVariable("weaknesses") List<PowerElement> weaknesses) {
        ModifyMonsterTypeDTO modifyDTO = new ModifyMonsterTypeDTO();
        modifyDTO.setId(id);
        modifyDTO.setName(name);
        modifyDTO.setFood(food);
        modifyDTO.setNewWeaknesses(weaknesses);
        monsterTypeFacade.editMonsterType(modifyDTO);
    }
}