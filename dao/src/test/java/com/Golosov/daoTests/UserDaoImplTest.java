package com.Golosov.daoTests;

import com.Golosov.builders.UserBuilder;
import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    private User expectedUser;
    private User actualUser;

    @Before
    public void setUp() {
        actualUser = new UserBuilder
                .UserEntityBuilder()
                .name("Andy")
                .surname("Golosov")
                .lastname("Dmitrievich")
                .email("qwerty@mail.ru")
                .password("creator")
                .dateOfBirth(LocalDate.now().minusYears(20))
                .registration(LocalDate.now())
                .build();
    }

    @Test
    @Rollback
    public void testSave() {
        long id = userDao.save(actualUser);

        expectedUser = userDao.getById((Long) id);
        Assert.assertEquals("testSave() method failed: ", actualUser, expectedUser);
    }


    @Test
    @Rollback
    public void testFindByEmail() {
        long id = userDao.save(actualUser);

        expectedUser = userDao.getByEmail(actualUser.getEmail());
        Assert.assertEquals("testFindByEmail() method failed: ", actualUser, expectedUser);
    }

    @Test
    @Rollback
    public void testDelete() {
        userDao.save(actualUser);

        userDao.delete(actualUser.getId());

        expectedUser = userDao.getById(actualUser.getId());
        Assert.assertNull("delete() method failed: ", expectedUser);
    }

    @Test
    @Rollback
    public void testGetById() {
        long id = userDao.save(actualUser);

        expectedUser = userDao.getById((Long) id);
        Assert.assertEquals("testGetById() method failed: ", actualUser, expectedUser);
    }

    @Test
    @Rollback
    public void testUpdate() {
        long id = userDao.save(actualUser);

        actualUser.setName("ROBOT");
        userDao.update(actualUser);

        expectedUser = userDao.getById((long) id);
        Assert.assertEquals("testUpdate() method failed: ", actualUser, expectedUser);
    }


    @Test
    @Rollback
    public void testGetAll() {
        userDao.save(actualUser);

        User user = new UserBuilder
                .UserEntityBuilder()
                .name("Andy")
                .surname("Golosov")
                .lastname("Dmitrievich")
                .email("qwerty@mail.ru")
                .password("creator")
                .dateOfBirth(LocalDate.now().minusYears(20))
                .registration(LocalDate.now())
                .build();

        userDao.save(user);

        List<User> users = userDao.getAll();
        Assert.assertTrue("testGetAll() method failed: ", users.size() >= 2);
    }

    @After
    public void dropDown() {
        actualUser = null;
        expectedUser = null;
    }
}
