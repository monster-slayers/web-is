package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.user.ChangeUserImageDTO;
import cz.muni.fi.pa165.monsterslayers.dto.user.UserDTO;
import cz.muni.fi.pa165.monsterslayers.enums.RightsLevel;
import cz.muni.fi.pa165.monsterslayers.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

/**
 * Rest controller for User
 *
 * @author Tomáš Richter
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @GetMapping
    Collection<UserDTO> getAllUsers(){
        return userFacade.getAllUsers();
    }

    @PutMapping(value = "/promote-rights/{id}/{rights}")
    private void promoteRights(@PathVariable("id") Long id, @PathVariable("rights") RightsLevel newRightsLevel){
        userFacade.changeRightsLevel(id, newRightsLevel);
    }

    @GetMapping(value = "/detail/{id}")
    public UserDTO getUserById(@PathVariable("id") Long id) {
	return userFacade.getUserById(id);
    }

    @PutMapping(value = "/modify-image")
    private void modifyImage(@RequestBody ChangeUserImageDTO changeDTO) {
        userFacade.changeUserImage(changeDTO);
    }

    @GetMapping(value = "/current")
    public UserDTO currentUser(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();

        if(principal == null){
            return null;
        }

        String userEmail = principal.getName();

        UserDTO user = userFacade.getUserByEmail(userEmail);

        // erase password before sending it to the user
        user.setPassword("");

        return user;
    }
}
