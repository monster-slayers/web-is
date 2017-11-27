package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.dto.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.ModifyClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.facade.ClientRequestFacade;
import cz.muni.fi.pa165.monsterslayers.service.ClientRequestService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createClientRequest(CreateClientRequestDTO createClientRequestDTO) {
        ClientRequest clientRequest = mappingService.mapTo(createClientRequestDTO, ClientRequest.class);
        clientRequestService.saveClientRequest(clientRequest);
    }

    @Override
    public void editClientRequest(ModifyClientRequestDTO modifyClientRequestDTO) {
        ClientRequest clientRequest = clientRequestService.findClientRequestById(
                modifyClientRequestDTO.getClientRequestId());
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
}
