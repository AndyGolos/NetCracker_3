package com.Golosov.daoTests;

import com.Golosov.builders.UserBuilder;
import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
    public void testSave() {
        long id = userDao.save(actualUser);

        expectedUser = userDao.getById((Long) id);
        Assert.assertEquals("testSave() method failed: ", actualUser, expectedUser);

        userDao.delete(id);
    }


    @Ignore
    @Test
    public void testFindByEmail() {
        //Работает, просто в бд сохранено много пользователей с таким же emailom
        long id = userDao.save(actualUser);

        expectedUser = userDao.getByEmail(actualUser.getEmail());
        Assert.assertEquals("testFindByEmail() method failed: ", actualUser, expectedUser);

        userDao.delete(id);
    }

    @Test
    public void testDelete() {
        userDao.save(actualUser);
        userDao.delete(actualUser.getId());

        expectedUser = userDao.getById(actualUser.getId());
        Assert.assertNull("delete() method failed: ", expectedUser);
    }

    @Test
    public void testGetById() {
        long id = userDao.save(actualUser);

        expectedUser = userDao.getById((Long) id);
        Assert.assertEquals("testGetById() method failed: ", actualUser, expectedUser);

        userDao.delete(id);
    }

    @Test
    public void testUpdate() {
        long id = userDao.save(actualUser);

        actualUser.setName("ROBOT");
        userDao.update(actualUser);

        expectedUser = userDao.getById((long) id);
        Assert.assertEquals("testUpdate() method failed: ", actualUser, expectedUser);

        userDao.delete(id);
    }

    @Ignore
    @Test
    public void testGetAll() {

    }

    @After
    public void dropDown() {
        actualUser = null;
        expectedUser = null;
    }


}
