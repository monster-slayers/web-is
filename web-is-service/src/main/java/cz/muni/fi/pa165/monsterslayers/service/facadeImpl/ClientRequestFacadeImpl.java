package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ModifyClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.facade.ClientRequestFacade;
import cz.muni.fi.pa165.monsterslayers.service.*;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Implementation of client request facade interface
 *
 * @author Maksym Tsuhui
 */
@Service
@Transactional
public class ClientRequestFacadeImpl implements ClientRequestFacade {
    @Autowired
    private ClientRequestService clientRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private MonsterTypeService monsterTypeService;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private HeroService heroService;

    @Override
    public ClientRequestDTO getClientRequestById(Long clientRequestId) {
        ClientRequest found = clientRequestService.findClientRequestById(clientRequestId);
        if (found == null) {
            return null;
        }
        return mappingService.mapTo(found, ClientRequestDTO.class);
    }

    @Override
    public ClientRequestDTO getClientRequestByTitle(String title) {
        ClientRequest found = clientRequestService.findClientRequestByTitle(title);
        if (found == null) {
            return null;
        }
        return mappingService.mapTo(found, ClientRequestDTO.class);
    }

    @Override
    public Collection<ClientRequestDTO> getAllClientRequests() {
        return mappingService.mapTo(clientRequestService.getAllClientRequests(), ClientRequestDTO.class);
    }

    @Override
    public void removeClientRequest(ClientRequestDTO clientRequestDTO) {
        clientRequestService.removeClientRequest(mappingService.mapTo(clientRequestDTO,ClientRequest.class));
    }

    @Override
    public Long createClientRequest(CreateClientRequestDTO createClientRequestDTO) {
        ClientRequest clientRequest = mappingService.mapTo(createClientRequestDTO, ClientRequest.class);
        clientRequest.setClient(userService.findUserById(createClientRequestDTO.getClientId()));
        clientRequest.setKillList(new HashMap<>());
        for (Map.Entry<Long, Integer> killListEntry : createClientRequestDTO.getKillList().entrySet()) {
            clientRequest.addToKillList(
                monsterTypeService.findById(killListEntry.getKey()), killListEntry.getValue()
            );
        }
        return clientRequestService.saveClientRequest(clientRequest);
    }

    @Override
    public void editClientRequest(ModifyClientRequestDTO modifyClientRequestDTO) {
        ClientRequest clientRequest = clientRequestService.findClientRequestById(
                modifyClientRequestDTO.getClientRequestId());
        clientRequest.setKillList(new HashMap<>());
        for (Map.Entry<Long, Integer> killListEntry : modifyClientRequestDTO.getKillList().entrySet()) {
            clientRequest.addToKillList(
                    monsterTypeService.findById(killListEntry.getKey()), killListEntry.getValue()
            );
        }
        if (modifyClientRequestDTO.getTitle() != null) {
            clientRequest.setTitle(modifyClientRequestDTO.getTitle());
        }
        if (modifyClientRequestDTO.getDescription() != null) {
            clientRequest.setDescription(modifyClientRequestDTO.getDescription());
        }
        if (modifyClientRequestDTO.getLocation() != null) {
            clientRequest.setLocation(modifyClientRequestDTO.getLocation());
        }
        if (modifyClientRequestDTO.getReward() != null) {
            clientRequest.setReward(modifyClientRequestDTO.getReward());
        }
        clientRequestService.saveClientRequest(clientRequest);
    }

    @Override
    public HeroDTO getBestHeroForClientRequest(Long clientRequestId) {
        ClientRequest clientRequest = clientRequestService.findClientRequestById(clientRequestId);

        Map<MonsterType, Integer> killList = clientRequest.getKillList();

        PowerElementsMatch bestMatch = new PowerElementsMatch(0, Integer.MAX_VALUE);
        Hero bestHero = new Hero();

        for (Hero hero : heroService.getAllHeroes()) {
            List<PowerElementsMatch> matches = new ArrayList<>();
            for (Map.Entry<MonsterType, Integer> entry : killList.entrySet()) {
                MonsterType monsterType = entry.getKey();
                matches.add(
                        heroService.countHeroSuitabilityAgainstMonsterType(hero, monsterType)
                                .multiplyByMonsterCount(entry.getValue())
                );
            }

            PowerElementsMatch match = PowerElementsMatch.sumMatches(matches);
            if (match.isMoreSuitable(bestMatch)) {
                bestMatch = match;
                bestHero = hero;
            }
        }

        return mappingService.mapTo(bestHero, HeroDTO.class);
    }
}
