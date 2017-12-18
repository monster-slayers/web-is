package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ModifyClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.dto.jobs.JobDTO;

import java.util.Collection;

/**
 * Client request facade interface
 *
 * @author Maksym Tsuhui
 */
public interface ClientRequestFacade {
    /**
     * Gets client request with specific id as DTO
     * 
     * @param clientRequestId specific id
     * @return single DTO
     */
    ClientRequestDTO getClientRequestById(Long clientRequestId);
    
    /**
     * Gets client request with specific title as DTO
     * 
     * @param title specific title
     * @return single DTO
     */
    ClientRequestDTO getClientRequestByTitle(String title);
    
    /**
     * Gets all client requests as collection of DTOs
     * 
     * @return collection of DTOs
     */
    Collection<ClientRequestDTO> getAllClientRequests();

    /**
     * Removes client request according to DTO
     * 
     * @param clientRequestDTO DTO of client request that will be removed
     */
    void removeClientRequest(ClientRequestDTO clientRequestDTO);
    
    /**
     * Edits client request according to special DTO
     * 
     * @param modifyClientRequestDTO DTO for modification of client request
     */
    void editClientRequest(ModifyClientRequestDTO modifyClientRequestDTO);
    
    /**
     * Creates new client request according to special DTO
     * 
     * @param createClientRequestDTO DTO for creating client request
     *
     * @return ID of newly created client request
     */
    Long createClientRequest(CreateClientRequestDTO createClientRequestDTO);

    /**
     * Gets Hero who's suited the best for the client request based on monsters requested to be killed.
     *
     * @param clientRequestId client request id id
     * @return The best suited hero
     */
    HeroDTO getBestHeroForClientRequest(Long clientRequestId);
}
