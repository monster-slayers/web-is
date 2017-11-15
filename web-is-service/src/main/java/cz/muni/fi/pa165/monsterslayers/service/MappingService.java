package cz.muni.fi.pa165.monsterslayers.service;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Interface for bean mapping (used for mapping entities to data transfer objects)
 * 
 * @author Tomáš Richter
 */

@Service
public interface MappingService {
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);
    public  <T> T mapTo(Object u, Class<T> mapToClass);
}
