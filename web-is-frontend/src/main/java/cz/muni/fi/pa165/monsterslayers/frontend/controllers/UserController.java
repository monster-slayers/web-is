package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.enums.UserStatus;
import cz.muni.fi.pa165.monsterslayers.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @GetMapping
    Collection<UserDTO> getAllHeroes(){
        return userFacade.getAllUsers();
    }

    @PutMapping(value = "/promote-rights/{id}/{rights}")
    private void promoteRights(@PathVariable("id") Long id, @PathVariable("rights") RightsLevel newRightsLevel){
        userFacade.changeRightsLevel(id, newRightsLevel);
    }

    @PutMapping(value = "/change-status/{id}/{status}")
    private void changeStatus(@PathVariable("id") Long id, @PathVariable("status") UserStatus userStatus){
        userFacade.changeUserStatus(id, userStatus);
    }
}
