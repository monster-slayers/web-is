package cz.muni.fi.pa165.monsterslayers.service;

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

import java.security.SecureRandom;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/service-context.xml"})
public class PasswordServiceTest {

    @Mock
    private SecureRandom secureRandom;

    @Autowired
    @InjectMocks
    private PasswordService passwordService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private final String hashPreamble =
            "1000:303030303030303030303030303030303030303030303030:";

    @Test
    public void testCreateHash() {
        doAnswer(invocationOnMock -> {
            byte[] salt1 = invocationOnMock.getArgumentAt(0, byte[].class);
            for(int i = 0; i < salt1.length; ++i) {
                salt1[i] = '0';
            }
            return null;
        }).when(secureRandom).nextBytes(any());

        String hash = passwordService.createHash("password");

        String expected = hashPreamble +
                "e28cc32ee56fdab1bf84e86d0bc28f967c44363a0598f609";

        Assert.assertEquals(expected, hash);
    }

    @Test
    public void testCheckHashTrue() {
        String hash = hashPreamble +
                "79c5bde78533132fd146c17093a8b115e8ba64bbb1560246";
        Assert.assertTrue(passwordService.checkHash("password2", hash));
    }

    @Test
    public void testCheckHashFalse() {
        String badHash = hashPreamble +
                "79c5bde78533132fd146c17093a8b115e8ba64bbb1560245";
        Assert.assertFalse(passwordService.checkHash("password2", badHash));

        String correctHash = "79c5bde78533132fd146c17093a8b115e8ba64bbb1560246";

        String badPreamble1 =
                "1001:303030303030303030303030303030303030303030303030:";
        Assert.assertFalse(passwordService.checkHash("password2", badPreamble1 + correctHash));

        String badPreamble2 =
                "1000:303030303030303030303030303030303030303030303031:";
        Assert.assertFalse(passwordService.checkHash("password2", badPreamble2 + correctHash));
    }
}
