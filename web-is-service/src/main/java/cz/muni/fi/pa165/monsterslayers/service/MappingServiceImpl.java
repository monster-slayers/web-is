package cz.muni.fi.pa165.monsterslayers.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.dozer.Mapper;

/**
 * Implementation of bean mapping service using Dozer framework.
 *
 * @author Tomáš Richter
 */

@Service
public class MappingServiceImpl implements MappingService {

    @Autowired
    private Mapper dozer;

    @Override
    public  <T> Collection<T> mapTo(Iterable<?> objects, Class<T> mapToClass) {
        Collection<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <K, V> Map<K, V> mapTo(Map<?, V> objects, Class<K> mapToClass) {
        Map<K, V> mappedMap = new HashMap<>();
        for(Map.Entry<?, V> entry : objects.entrySet()){
            mappedMap.put(dozer.map(entry.getKey(), mapToClass), entry.getValue());
        }

        return mappedMap;
    }

    @Override
    public  <T> T mapTo(Object object, Class<T> mapToClass)
    {
        return dozer.map(object, mapToClass);
    }
}
