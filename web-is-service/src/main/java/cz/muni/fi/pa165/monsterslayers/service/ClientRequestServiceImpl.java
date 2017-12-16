package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.ClientRequestRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.exception.MonsterSlayersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of client request service
 *
 * @author Maksym Tsuhui
 */
@Service
public class ClientRequestServiceImpl implements ClientRequestService {
    @Autowired
    private ClientRequestRepository clientRequestRepository;

    @Override
    public ClientRequest findClientRequestById(Long id) {
        try {
            return clientRequestRepository.findOne(id);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot find client request with ID" + id.toString() + ".",
                    e
            );
        }
    }

    @Override
    public Iterable<ClientRequest> getAllClientRequests() {
        try {
            return clientRequestRepository.findAll();
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot retrieve client requests.",
                    e
            );
        }
    }

    @Override
    public ClientRequest findClientRequestByTitle(String title) {
        try {
            return clientRequestRepository.findByTitle(title);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot retrieve client request with title" + title + ".",
                    e
            );
        }
    }

    @Override
    public void removeClientRequest(ClientRequest clientRequest) {
        try {
            clientRequestRepository.delete(clientRequest);
        } catch (Exception e) {
            throw new MonsterSlayersException(
                    "Cannot remove client request.",
                    e
            );
        }
    }

    @Override
    public Long saveClientRequest(ClientRequest clientRequest) {
        try {
            return clientRequestRepository.save(clientRequest).getId();
        } catch(Exception e) {
            throw new MonsterSlayersException(
                    "Cannot save client request.",
                    e
            );
        }
    }
}
