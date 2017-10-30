package cz.muni.fi.pa165.monsterslayers.tests;

import cz.muni.fi.pa165.monsterslayers.dao.ClientRequestRepository;
import cz.muni.fi.pa165.monsterslayers.dao.MonsterTypeRepository;
import cz.muni.fi.pa165.monsterslayers.dao.UserRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test class for ClientRequest entity (case for constraints, setters, equals)
 * 
 * @author Tomáš Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
@Transactional
public class ClientRequestTests {  
    @PersistenceContext
    EntityManager em;
    
    @Autowired
    private ClientRequestRepository clientRequestRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MonsterTypeRepository monsterTypeRepository;
    
    private ClientRequest clientRequest;
    private ClientRequest clientRequestFull;
    
    private User user;
    private MonsterType monsterType = new MonsterType("Zombie");
    private String title = "request title";
    private String description = "description";
    private String location = "location";
    private BigDecimal reward = new BigDecimal("1000");
    private Integer monsterCount = 10;
    
    @Before
    public void setup() {
        user = new User();
        user.setName("Monty Pythons");
        user.setEmail("monty@python.com");
        user.setPassword("bravesirrobin");
        userRepository.save(user);
        
        clientRequest = new ClientRequest();
        
        clientRequestFull = new ClientRequest(); 
        clientRequestFull.setTitle(title);
        clientRequestFull.setClient(user);
        clientRequestFull.setDescription(description);
        clientRequestFull.setLocation(location);
        clientRequestFull.setReward(reward);
        clientRequestFull.addToKillList(monsterType, monsterCount);   
        clientRequestRepository.save(clientRequestFull); 
    }
    
    @Test
    public void allAttributesTest() {
        ClientRequest found = clientRequestRepository.findOne(clientRequestFull.getId());
        Assert.assertEquals(title, found.getTitle());
        Assert.assertEquals(user, found.getClient());
        Assert.assertEquals(description, found.getDescription());
        Assert.assertEquals(location, found.getLocation());
        Assert.assertEquals(reward, found.getReward());
        Assert.assertEquals(monsterCount, found.getCountFromKillList(monsterType));
    }
    
    @Test
    public void isInKillListTest() {
        Assert.assertTrue(clientRequestFull.isInKillList(monsterType));
        clientRequestFull.removeFromKillList(monsterType);
        Assert.assertFalse(clientRequestFull.isInKillList(monsterType));
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void saveNullClientTest() {
        clientRequest.setTitle("Null client");
        clientRequestRepository.save(clientRequest);
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void saveNullTitleTest() {
        clientRequest.setClient(user);
        clientRequestRepository.save(clientRequest);
    }
    
    @Test(expected = DataAccessException.class)
    public void saveUniqueTitleTest() {
        clientRequest.setClient(user);
        clientRequest.setTitle(clientRequestFull.getTitle());
        clientRequestRepository.save(clientRequest);
    }
    
    @Test
    public void equalityTest() {
        Assert.assertEquals(clientRequest, clientRequest);
        Assert.assertNotEquals(clientRequest, null);
        
        clientRequest.setTitle(clientRequestFull.getTitle());
        clientRequest.setClient(user);
        Assert.assertEquals(clientRequestFull, clientRequest);
        
        clientRequest.setTitle("other title");
        Assert.assertNotEquals(clientRequestFull, clientRequest);
    }
    
    @Test
    public void hashCodeTest() {
        clientRequest.setTitle(clientRequestFull.getTitle());
        clientRequest.setClient(user);
        Assert.assertEquals(clientRequestFull.hashCode(), clientRequest.hashCode());
    }
}
