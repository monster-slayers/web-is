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
    <T> Collection<T> mapTo(Iterable<?> objects, Class<T> mapToClass);
    <K, V> Map<K, V> mapTo(Map<?, V> objects, Class<K> mapToClass);
    <T> T mapTo(Object u, Class<T> mapToClass);
}
