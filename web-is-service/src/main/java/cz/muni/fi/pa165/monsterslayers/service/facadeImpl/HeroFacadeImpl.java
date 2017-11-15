package cz.muni.fi.pa165.monsterslayers.service.facadeImpl;

import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of hero facade interface.
 * Uses hero service and DTO.
 * 
 * @author Tomáš Richter
 */

@Service
@Transactional
public class HeroFacadeImpl {
    
    @Autowired
    private HeroService heroService;

    @Autowired
    private MappingService mappingService;
}
