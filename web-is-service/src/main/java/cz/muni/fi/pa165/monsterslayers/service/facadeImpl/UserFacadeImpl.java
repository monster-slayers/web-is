package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of hero facade interface.
 * Uses user service and DTO (DTO for authetication as well).
 * 
 * @author Tomáš Richter
 */

@Service
@Transactional
public class UserFacadeImpl {
    
    @Autowired
    private UserService userService;

    @Autowired
    private MappingService mappingService;
}
