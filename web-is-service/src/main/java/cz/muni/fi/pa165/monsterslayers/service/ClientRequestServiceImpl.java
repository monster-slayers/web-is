package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.ClientRequestRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of client request service
 *
 * @author Maksym Tsuhui
 */
public class ClientRequestServiceImpl implements ClientRequestService {
    @Autowired
    private ClientRequestRepository clientRequestRepository;

    @Override
    public ClientRequest findClientRequestById(Long id) {
        return clientRequestRepository.findOne(id);
    }

    @Override
    public Iterable<ClientRequest> getAllClientRequests() {
        return clientRequestRepository.findAll();
    }

    @Override
    public ClientRequest findClientRequestByTitle(String title) {
        return clientRequestRepository.findByTitle(title);
    }

    @Override
    public void removeClientRequest(ClientRequest clientRequest) {
        clientRequestRepository.delete(clientRequest);
    }

    @Override
    public void saveClientRequest(ClientRequest clientRequest) {
        clientRequestRepository.save(clientRequest);
    }

    @Override
    public void addMonstersToClientRequest(ClientRequest clientRequest, MonsterType monsterType, int count) {
        clientRequest.addToKillList(monsterType, count);
    }

    @Override
    public void removeMonstersFromClientRequest(ClientRequest clientRequest, MonsterType monsterType) {
        clientRequest.removeFromKillList(monsterType);
    }
}
