package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import org.springframework.data.repository.CrudRepository;

public interface ClientRequestRepository extends CrudRepository<ClientRequest, Long> {
    public ClientRequest findByTitle(String title);
}
