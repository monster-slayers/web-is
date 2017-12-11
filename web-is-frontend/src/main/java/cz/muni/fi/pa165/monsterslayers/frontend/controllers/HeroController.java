package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.hero.CreateHeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.enums.HeroStatus;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.facade.HeroFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @PostMapping(value = "/create/{userId}/{heroName}")
    private void createHero(@PathVariable("userId") Long userId, @PathVariable("heroName") String heroName) {
        CreateHeroDTO createHeroDTO = new CreateHeroDTO(userId, heroName);
        heroFacade.createHero(createHeroDTO);
    }

    @PutMapping(value = "/change-status/{id}/{status}")
    private void changeStatus(@PathVariable("id") Long id, @PathVariable("status") HeroStatus userStatus){
        heroFacade.changeStatus(id, userStatus);
    }
}
