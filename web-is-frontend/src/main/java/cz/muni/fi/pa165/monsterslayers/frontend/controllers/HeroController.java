package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.hero.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.ModifyHeroDTO;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.facade.HeroFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Rest controller for Hero
 *
 * @author Tomáš Richter
 */
@RestController
@RequestMapping("/hero")
public class HeroController {

    @Autowired
    private HeroFacade heroFacade;

    @GetMapping
    Collection<HeroDTO> getAllHeroes(){
        return heroFacade.getAllHeroes();
    }

    @DeleteMapping(value = "/delete/{id}")
    private void deleteHero(@PathVariable("id") Long id){
        heroFacade.removeHero(heroFacade.getHeroById(id));
    }

    @PostMapping(value = "/create/{userId}/{heroName}/{elements}")
    private void createHero(@PathVariable("userId") Long userId, @PathVariable("heroName") String heroName, @PathVariable("elements") List<PowerElement> elements) {
        CreateHeroDTO createHeroDTO = new CreateHeroDTO(userId, heroName);
        createHeroDTO.setElements(elements);
        heroFacade.createHero(createHeroDTO);
    }

    @PutMapping(value = "/modify/{heroId}/{heroName}/{elements}")
    private void modifyHero(@PathVariable("heroId") Long heroId, @PathVariable("heroName") String heroName, @PathVariable("elements") List<PowerElement> elements) {
        ModifyHeroDTO modifyHeroDTO = new ModifyHeroDTO();
        modifyHeroDTO.setHeroId(heroId);
        modifyHeroDTO.setNewHeroName(heroName);
        modifyHeroDTO.setNewElements(elements);
        heroFacade.editHero(modifyHeroDTO);
    }

    @PutMapping(value = "/change-status/{id}/{status}")
    private void changeStatus(@PathVariable("id") Long id, @PathVariable("status") HeroStatus userStatus){
        heroFacade.changeStatus(id, userStatus);
    }
}
