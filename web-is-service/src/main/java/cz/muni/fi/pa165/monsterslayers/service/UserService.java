/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.entities.User;
import java.util.List;

/**
 *
 * @author Tomáš Richter
 */
public interface UserService {
    //Basic crud operations
    User findById(Long id);
    Iterable<User> findAll();
    void createOrUpdateIfExists(User user);
    void remove(User user);
    
    //Custom crud operations
    List<User> findByName(String name);
    User findByEmail(String email);
    
    //Business methods
}
