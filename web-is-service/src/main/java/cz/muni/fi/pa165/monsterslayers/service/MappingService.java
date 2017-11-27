package cz.muni.fi.pa165.monsterslayers.service;
import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Interface for bean mapping (used for mapping entities to data transfer objects)
 *
 * @author Tomáš Richter
 */

@Service
public interface MappingService {
    /**
     * Version of mapping for collections
     * 
     * @param <T>
     * @param objects objects that will be mapped
     * @param mapToClass destination class for mapping
     * @return collection of mapped objects
     */
    <T> Collection<T> mapTo(Iterable<?> objects, Class<T> mapToClass);
    
    /**
     * Version of mapping for maps (dictionaries)
     * 
     * @param <K>
     * @param <V>
     * @param objects objects that will be mapped
     * @param mapToClass destination class for mapping
     * @return map of mapped objects
     */
    <K, V> Map<K, V> mapTo(Map<?, V> objects, Class<K> mapToClass);
    
    /**
     * Version of mapping for simple objects
     * 
     * @param <T>
     * @param u object that will be mapped
     * @param mapToClass destination class for mapping
     * @return mapped object
     */
    <T> T mapTo(Object u, Class<T> mapToClass);
}
