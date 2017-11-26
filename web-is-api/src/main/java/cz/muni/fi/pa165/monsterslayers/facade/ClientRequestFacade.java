package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.ModifyClientRequestDTO;

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
    void editClientRequest(ModifyClientRequestDTO modifyClientRequestDTO);
    void createClientRequest(CreateClientRequestDTO createClientRequestDTO);
}
