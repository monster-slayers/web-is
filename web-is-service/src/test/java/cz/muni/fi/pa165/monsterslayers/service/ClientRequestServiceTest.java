package cz.muni.fi.pa165.monsterslayers.service;

import cz.muni.fi.pa165.monsterslayers.dao.ClientRequestRepository;
import cz.muni.fi.pa165.monsterslayers.entities.ClientRequest;
import cz.muni.fi.pa165.monsterslayers.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for ClientRequestService.
 *
 * @author David Kizivat
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/service-context.xml")
public class ClientRequestServiceTest {
    @Mock
    private ClientRequestRepository repository;

    @Autowired
    @InjectMocks
    private ClientRequestService service;

    @Mock
    private User client;

    private ClientRequest cr1;
    private ClientRequest cr2;
    private Collection<ClientRequest> crs = new ArrayList<>();
    private boolean initialized = false;

    @Before
    public void setUp() {
        if (initialized) {
            return;
        }
        MockitoAnnotations.initMocks(this);

        initialized = true;
        cr1 = new ClientRequest("cr1", client);
        cr1.setId(1L);
        cr2 = new ClientRequest("cr2", client);
        cr2.setId(2L);
    }

    @Test
    public void findClientRequestById_IdExistsTest() {
        when(repository.findOne(cr1.getId())).thenReturn(cr1);
        ClientRequest result = service.findClientRequestById(1L);
        verify(repository).findOne(1L);
        Assert.assertEquals(cr1, result);
    }

    @Test
    public void findClientRequestById_IdDoesNotExistText() {
        when(repository.findOne(cr1.getId())).thenReturn(null);
        ClientRequest result = service.findClientRequestById(1L);
        verify(repository).findOne(1L);
        Assert.assertNull(result);
    }

    @Test
    public void getAllClientRequestsTest() {
        when(repository.findAll()).thenReturn(crs);
        Assert.assertEquals(crs, service.getAllClientRequests());
        crs.add(cr1);
        Assert.assertEquals(crs, service.getAllClientRequests());
        crs.add(cr2);
        Assert.assertEquals(crs, service.getAllClientRequests());
        verify(repository, atLeast(3)).findAll();
    }

    @Test
    public void getAllClientRequestByTitle_TitleExistsTest() {
        when(repository.findByTitle(cr1.getTitle())).thenReturn(cr1);
        ClientRequest result = service.findClientRequestByTitle("cr1");
        verify(repository).findByTitle("cr1");
        Assert.assertEquals(cr1, result);
    }

    @Test
    public void findClientRequestByTitle_TitleDoesNotExistText() {
        when(repository.findByTitle(cr1.getTitle())).thenReturn(null);
        ClientRequest result = service.findClientRequestByTitle("cr3");
        verify(repository).findByTitle("cr3");
        Assert.assertNull(result);
    }

    @Test
    public void removeClientRequestTest() {
        service.removeClientRequest(cr1);

        ArgumentCaptor<ClientRequest> crCaptor = ArgumentCaptor.forClass(ClientRequest.class);
        verify(repository, atLeast(0)).delete(crCaptor.capture());

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(repository, atLeast(0)).delete(idCaptor.capture());

        Assert.assertTrue(
                (!crCaptor.getAllValues().isEmpty() && crCaptor.getValue().equals(cr1)) ||
                         (!idCaptor.getAllValues().isEmpty() && idCaptor.getValue().equals(cr1.getId()))
        );
    }

    @Test
    public void saveClientRequestTest() {
        when(repository.save(cr1)).thenReturn(cr1);
        service.saveClientRequest(cr1);
        verify(repository).save(cr1);
    }
}

