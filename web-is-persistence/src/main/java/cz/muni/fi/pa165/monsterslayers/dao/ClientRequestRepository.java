package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import org.springframework.data.repository.CrudRepository;

public interface ClientRequestRepository extends CrudRepository<ClientRequest, Long> {
    /**
     * Finds client request with specified title (title is unique)
     * @param title specified title
     * @return client request with specified title
     */
    public ClientRequest findByTitle(String title);
}
