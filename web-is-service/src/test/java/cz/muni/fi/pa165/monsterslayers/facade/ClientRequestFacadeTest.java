package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ModifyClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.hero.HeroDTO;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.Hero;
import cz.muni.fi.pa165.monsterslayers.entities.MonsterType;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import cz.muni.fi.pa165.monsterslayers.enums.PowerElement;
import cz.muni.fi.pa165.monsterslayers.service.ClientRequestService;
import cz.muni.fi.pa165.monsterslayers.service.HeroService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import cz.muni.fi.pa165.monsterslayers.service.UserService;
import cz.muni.fi.pa165.monsterslayers.service.utils.PowerElementsMatch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for simple facade tests of ClientRequest
 *
 * @author Tomáš Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class ClientRequestFacadeTest {

    @Mock
    private ClientRequestService clientRequestService;

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private ClientRequestFacade clientRequestFacade;

    @Mock
    private MappingService mappingService;

    @Mock
    private HeroService heroService;

    private ClientRequestDTO clientRequestDTO = new ClientRequestDTO();

    private ModifyClientRequestDTO modifyClientRequestDTO = new ModifyClientRequestDTO();

    private CreateClientRequestDTO createClientRequestDTO = new CreateClientRequestDTO();

    private ClientRequest clientRequest = new ClientRequest();

    private User user = new User();

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getByIdCallsServiceProperlyTest(){
        Long clientRequestId = 27L;

        when(clientRequestService.findClientRequestById(clientRequestId)).thenReturn(clientRequest);
        when(mappingService.mapTo(clientRequest, ClientRequestDTO.class)).thenReturn(clientRequestDTO);

        ClientRequestDTO found = clientRequestFacade.getClientRequestById(clientRequestId);

        verify(clientRequestService).findClientRequestById(clientRequestId);
        verify(mappingService).mapTo(clientRequest, ClientRequestDTO.class);

        Assert.assertEquals(clientRequestDTO, found);
    }

    @Test
    public void getByTitleCallsServiceProperlyTest() {
        String clientRequestTitle = "Request title";

        when(clientRequestService.findClientRequestByTitle(clientRequestTitle)).thenReturn(clientRequest);
        when(mappingService.mapTo(clientRequest, ClientRequestDTO.class)).thenReturn(clientRequestDTO);

        ClientRequestDTO found = clientRequestFacade.getClientRequestByTitle(clientRequestTitle);

        verify(clientRequestService).findClientRequestByTitle(clientRequestTitle);
        verify(mappingService).mapTo(clientRequest, ClientRequestDTO.class);

        Assert.assertEquals(clientRequestDTO, found);
    }

    @Test
    public void getAllCallsServiceProperlyTest() {
        Collection<ClientRequest> list = Collections.singletonList(clientRequest);
        Collection<ClientRequestDTO> listDTO = Collections.singletonList(clientRequestDTO);

        when(clientRequestService.getAllClientRequests()).thenReturn(list);
        when(mappingService.mapTo(list, ClientRequestDTO.class)).thenReturn(listDTO);

        Collection<ClientRequestDTO> found = clientRequestFacade.getAllClientRequests();

        verify(clientRequestService).getAllClientRequests();
        verify(mappingService).mapTo(list, ClientRequestDTO.class);

        Assert.assertEquals(listDTO, found);
    }

    @Test
    public void removeCallsServiceProperlyTest() {
        when(mappingService.mapTo(clientRequestDTO, ClientRequest.class)).thenReturn(clientRequest);

        clientRequestFacade.removeClientRequest(clientRequestDTO);

        verify(clientRequestService).removeClientRequest(clientRequest);
    }

    @Test
    public void editCallsServiceProperlyTest() {
        when(clientRequestService.findClientRequestById(modifyClientRequestDTO.getClientRequestId())).thenReturn(clientRequest);

        clientRequestFacade.editClientRequest(modifyClientRequestDTO);

        verify(clientRequestService).saveClientRequest(clientRequest);
    }

    @Test
    public void createCallsServiceProperlyTest() {
        when(mappingService.mapTo(createClientRequestDTO, ClientRequest.class)).thenReturn(clientRequest);
        when(userService.findUserById(createClientRequestDTO.getClientId())).thenReturn(user);

        clientRequestFacade.createClientRequest(createClientRequestDTO);

        verify(clientRequestService).saveClientRequest(clientRequest);
    }

    @Test
    public void testGetBestHeroForClientRequest(){
        Long clientRequestId = 77L;

        ClientRequest clientRequest = new ClientRequest();

        MonsterType monsterType1 = new MonsterType();
        monsterType1.addWeakness(PowerElement.GHOST);
        monsterType1.setName("Java");
        clientRequest.addToKillList(monsterType1, 2);

        MonsterType monsterType2 = new MonsterType();
        monsterType2.addWeakness(PowerElement.EARTH);
        monsterType2.setName("Oracle");
        clientRequest.addToKillList(monsterType2, 3);

        when(clientRequestService.findClientRequestById(clientRequestId)).thenReturn(clientRequest);


        List<Hero> heroes = new ArrayList<>();
        Hero hero1 = new Hero();
        hero1.addElement(PowerElement.EARTH);
        heroes.add(hero1);

        Hero hero2 = new Hero();
        hero2.addElement(PowerElement.POISON);
        heroes.add(hero2);

        when(heroService.getAllHeroes()).thenReturn(heroes);

        HeroDTO heroDTO = new HeroDTO();

        when(mappingService.mapTo(hero1, HeroDTO.class)).thenReturn(heroDTO);

        when(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType1)).thenReturn(new PowerElementsMatch(0, 1));
        when(heroService.countHeroSuitabilityAgainstMonsterType(hero1, monsterType2)).thenReturn(new PowerElementsMatch(1, 0));
        when(heroService.countHeroSuitabilityAgainstMonsterType(hero2, monsterType1)).thenReturn(new PowerElementsMatch(0, 1));
        when(heroService.countHeroSuitabilityAgainstMonsterType(hero2, monsterType2)).thenReturn(new PowerElementsMatch(0, 1));

        HeroDTO bestHeroForJob = clientRequestFacade.getBestHeroForClientRequest(clientRequestId);

        Assert.assertEquals(heroDTO, bestHeroForJob);
    }
}
