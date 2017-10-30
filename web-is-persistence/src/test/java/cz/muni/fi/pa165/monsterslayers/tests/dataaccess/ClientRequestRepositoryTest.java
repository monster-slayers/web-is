package cz.muni.fi.pa165.monsterslayers.tests.dataaccess;

import cz.muni.fi.pa165.monsterslayers.dao.ClientRequestRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Test class for ClientRequestDaoImpl
 *
 * @author Tomáš Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
@Transactional
public class ClientRequestRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ClientRequestRepository clientRequestRepository;
    
    private User user;
    private ClientRequest clientRequest1;
    private ClientRequest clientRequest2;
    private ClientRequest clientRequestNotPersisted;
    
    //for saveAllAttributesTest (using initialized attributes to check set methods of entity as well)
    private final String title = "title of request";
    private final String description = "description of request";
    private final String location = "where are the monsters";
    private final BigDecimal reward = new BigDecimal("1000");
    private final MonsterType monsterType = new MonsterType("Zombie for ClientRequestTest");
    private final Integer monsterCount = 10;
    
    @Before
    public void setup() {
        user = new User();
        user.setName("Monty Python");
        user.setEmail("monty@python.com");
        user.setPassword("bravesirrobin");
        entityManager.persist(user);
        
        clientRequest1 = new ClientRequest();
        clientRequest1.setTitle("Client request no. 1");
        clientRequest1.setClient(user);
        entityManager.persist(clientRequest1);
        
        clientRequest2 = new ClientRequest();
        clientRequest2.setTitle("Client request no. 2");
        clientRequest2.setClient(user);
        entityManager.persist(clientRequest2);
        
        clientRequestNotPersisted = new ClientRequest();
        clientRequestNotPersisted.setTitle(title);
        clientRequestNotPersisted.setClient(user);
    }
    
    @Test
    public void countTest() {
        Assert.assertEquals(2, clientRequestRepository.count());
    }
    
    @Test
    public void findAllTest() {
        List<ClientRequest> found = (List<ClientRequest>) clientRequestRepository.findAll();
        Assert.assertEquals(2, found.size());
    }
    
    @Test
    public void findOneByIdTest() {
        ClientRequest found = clientRequestRepository.findOne(clientRequest1.getId());
        Assert.assertEquals(clientRequest1.getTitle(), found.getTitle());
    }
    
    @Test
    public void findPersistedByTitleTest() {
        ClientRequest found = clientRequestRepository.findByTitle(clientRequest2.getTitle());
        Assert.assertEquals(clientRequest2.getTitle(), found.getTitle());
    }
    
    @Test
    public void findNotPersistedByTitleTest() {
        ClientRequest found = clientRequestRepository.findByTitle(clientRequestNotPersisted.getTitle());
        Assert.assertNull(found);
    }
    
    @Test
    public void saveAllAttributesTest() {
        clientRequestNotPersisted.setDescription(description);
        clientRequestNotPersisted.setLocation(location);
        clientRequestNotPersisted.setReward(reward);
        clientRequestNotPersisted.addToKillList(monsterType, monsterCount);
        clientRequestRepository.save(clientRequestNotPersisted);
        
        ClientRequest found = clientRequestRepository.findOne(clientRequestNotPersisted.getId());
        Assert.assertEquals(title, found.getTitle());
        Assert.assertEquals(user, found.getClient());
        Assert.assertEquals(description, found.getDescription());
        Assert.assertEquals(location, found.getLocation());
        Assert.assertEquals(reward, found.getReward());
        Assert.assertEquals(monsterCount, found.getCountFromKillList(monsterType));
    }
    
    @Test
    public void saveValidTest() {
        clientRequestRepository.save(clientRequestNotPersisted);
        ClientRequest found = clientRequestRepository.findOne(clientRequestNotPersisted.getId());
        Assert.assertEquals(clientRequestNotPersisted.getTitle(), found.getTitle());
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void saveNullClientTest() {
        clientRequestNotPersisted.setClient(null);
        clientRequestRepository.save(clientRequestNotPersisted);
    }
    
    /*PROBLEMATIC
    @Test(expected = ConstraintViolationException.class)
    public void saveNullTitleTest() {
        clientRequestNotPersisted.setTitle(null);
        clientRequestRepository.save(clientRequestNotPersisted);
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void saveUniqueTitleTest() {
        clientRequestNotPersisted.setTitle(clientRequest1.getTitle());
        clientRequestRepository.save(clientRequestNotPersisted);
    }*/
   
    @Test
    public void updatePersistedTest() {
        clientRequest1.setTitle("updated title");
        clientRequestRepository.save(clientRequest1);
        
        Assert.assertEquals(2,clientRequestRepository.count());
        ClientRequest found = clientRequestRepository.findOne(clientRequest1.getId());
        Assert.assertEquals(clientRequest1.getTitle(), found.getTitle());
    }
    
    @Test
    public void deleteAllTest() {
        clientRequestRepository.deleteAll();
        Assert.assertEquals(0,clientRequestRepository.count());
    }
    
    @Test
    public void deleteByIdTest() {
        clientRequestRepository.delete(clientRequest1.getId());
        ClientRequest rest = ((List<ClientRequest>)clientRequestRepository.findAll()).get(0);
        Assert.assertEquals(1,clientRequestRepository.count());
        Assert.assertEquals(clientRequest2.getTitle(), rest.getTitle());
    }
    
    @Test
    public void deletePersistedTest() {
        clientRequestRepository.delete(clientRequest1);
        ClientRequest rest = ((List<ClientRequest>)clientRequestRepository.findAll()).get(0);
        Assert.assertEquals(1,clientRequestRepository.count());
        Assert.assertEquals(clientRequest2.getTitle(), rest.getTitle());
    }
    
    @Test
    public void deleteNotPersistedTest() {
        clientRequestRepository.delete(clientRequestNotPersisted);
        Assert.assertEquals(2,clientRequestRepository.count());   
    }
}
