package cz.muni.fi.pa165.monsterslayers.tests;

import cz.muni.fi.pa165.monsterslayers.ApplicationContext;
import cz.muni.fi.pa165.monsterslayers.dao.UserDao;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.PublicClassValidator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;

/**
 * Test class for ClientRequestDaoImpl
 *
 * @author Tomáš Richter
 */
@ContextConfiguration(classes = ApplicationContext.class)
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientRequestDaoTest {
    @Inject
    private UserDao userDao;

    @Test
    public void sample(){
        userDao.findAll();
    }
}
