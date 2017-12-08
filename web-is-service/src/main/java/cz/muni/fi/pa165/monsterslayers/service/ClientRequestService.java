package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;

/**
 * Client request service interface
 *
 * @author Maksym Tsuhui
 */

public interface ClientRequestService {
    /**
     * Finds client request by given id
     * 
     * @param id given id
     * @return found client request entity
     */
    ClientRequest findClientRequestById(Long id);
    
    /**
     * Finds all client requests
     * 
     * @return iterable collection of client request entities
     */
    Iterable<ClientRequest> getAllClientRequests();
    
    /**
     * Finds client request with given title
     * 
     * @param title given title
     * @return found client request entity
     */
    ClientRequest findClientRequestByTitle(String title);

    /**
     * Removes client request
     * 
     * @param clientRequest client request that will be removed
     */
    void removeClientRequest(ClientRequest clientRequest);
    
    /**
     * Saves (creates or updates) client request
     * 
     * @param clientRequest client request that will be saved
     *
     * @return ID of newly created client request
     */
    Long saveClientRequest(ClientRequest clientRequest);
}
