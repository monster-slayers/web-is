package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;

/**
 * Client request service interface
 *
 * @author Maksym Tsuhui
 */

public interface ClientRequestService {
    ClientRequest findClientRequestById(Long id);
    Iterable<ClientRequest> getAllClientRequests();
    ClientRequest findClientRequestByTitle(String title);

    void removeClientRequest(ClientRequest clientRequest);
    void saveClientRequest(ClientRequest clientRequest);
}
