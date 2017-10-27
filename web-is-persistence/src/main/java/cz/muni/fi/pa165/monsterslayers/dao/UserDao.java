package cz.muni.fi.pa165.monsterslayers.dao;

import cz.muni.fi.pa165.monsterslayers.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User's data access object interface
 *
 * @author Tomáš Richter
 */
public interface UserDao extends CrudRepository<User, Long> {
}
