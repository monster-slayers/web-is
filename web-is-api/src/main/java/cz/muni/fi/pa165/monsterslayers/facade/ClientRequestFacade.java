package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.CreateClientRequestDTO;

import java.util.Collection;

/**
 * Client request facade interface
 *
 * @author Maksym Tsuhui
 */
public interface ClientRequestFacade {
    ClientRequestDTO getClientRequestById(Long clientRequestId);
    ClientRequestDTO getClientRequestByTitle(String title);
    Collection<ClientRequestDTO> getAllClientRequests();

    void removeClientRequest(ClientRequestDTO clientRequestDTO);
    void editClientRequest(ClientRequestDTO clientRequestDTO);
    void createClientRequest(CreateClientRequestDTO createClientRequestDTO);
}
