package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.facade.ClientRequestFacade;
import cz.muni.fi.pa165.monsterslayers.service.ClientRequestService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

/**
 * Implementation of client request facade interface
 *
 * @author Maksym Tsuhui
 */
public class ClientRequestFacadeImpl implements ClientRequestFacade {
    @Autowired
    private ClientRequestService clientRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private MappingService mappingService;

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
    public void editClientRequest(ClientRequestDTO clientRequestDTO) {
        clientRequestService.saveClientRequest(mappingService.mapTo(clientRequestDTO,ClientRequest.class));
    }

    @Override
    public void createClientRequest(CreateClientRequestDTO createClientRequestDTO) {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setDescription(createClientRequestDTO.getDescription());
        clientRequest.setLocation(createClientRequestDTO.getLocation());
        clientRequest.setReward(createClientRequestDTO.getReward());
        clientRequest.setTitle(createClientRequestDTO.getTitle());
        clientRequest.setClient(userService.findUserById(createClientRequestDTO.getClientId()));
        clientRequest.setKillList(mappingService.mapTo(createClientRequestDTO.getKillList(), MonsterType.class));
        clientRequestService.saveClientRequest(clientRequest);
    }
}
