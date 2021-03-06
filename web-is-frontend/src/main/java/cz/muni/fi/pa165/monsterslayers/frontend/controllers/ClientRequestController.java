package cz.muni.fi.pa165.monsterslayers.frontend.controllers;

import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ModifyClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.JobDTO;
import cz.muni.fi.pa165.monsterslayers.facade.ClientRequestFacade;
import cz.muni.fi.pa165.monsterslayers.facade.JobFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Client Request Controller
 *
 * @author David Kizivat
 */
@RestController
@RequestMapping("/client-request")
public class ClientRequestController {
    @Autowired
    ClientRequestFacade facade;

    @Autowired
    JobFacade jobFacade;

    @GetMapping
    Iterable<ClientRequestDTO> getAllClientRequests() {
        return facade.getAllClientRequests();
    }

    @GetMapping(value = "/suggested-hero/{id}")
    HeroDTO getSuggestedHero(@PathVariable Long id) {
        return facade.getBestHeroForClientRequest(id);
    }

    @PostMapping(value = "/create")
    private void createClientRequest(@RequestBody CreateClientRequestDTO dto) {
        facade.createClientRequest(dto);
    }

    @PutMapping(value = "/modify/{id}")
    private void createClientRequest(@PathVariable Long id, @RequestBody ModifyClientRequestDTO dto) {
        dto.setClientRequestId(id);
        facade.editClientRequest(dto);
    }
}
