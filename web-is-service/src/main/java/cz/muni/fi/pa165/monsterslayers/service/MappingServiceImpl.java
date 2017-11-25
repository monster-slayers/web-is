package cz.muni.fi.pa165.monsterslayers.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    public  <T> List<T> mapTo(Iterable<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(dozer.map(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public  <T> T mapTo(Object object, Class<T> mapToClass)
    {
        return dozer.map(object, mapToClass);
    }
}
