package cz.muni.fi.pa165.monsterslayers.facade;

import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.CreateClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.dto.clientrequest.ModifyClientRequestDTO;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.service.ClientRequestService;
import cz.muni.fi.pa165.monsterslayers.service.MappingService;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Autowired
    @InjectMocks
    private ClientRequestFacade clientRequestFacade;

    @Mock
    private MappingService mappingService;

    private ClientRequestDTO clientRequestDTO = new ClientRequestDTO();

    private ModifyClientRequestDTO modifyClientRequestDTO = new ModifyClientRequestDTO();

    private CreateClientRequestDTO createClientRequestDTO = new CreateClientRequestDTO();

    private ClientRequest clientRequest = new ClientRequest();

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

        clientRequestFacade.createClientRequest(createClientRequestDTO);

        verify(clientRequestService).saveClientRequest(clientRequest);
    }
}
