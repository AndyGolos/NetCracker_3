package com.golosov.daoTests;

import com.golosov.builders.RoleBuilder;
import com.golosov.dao.interfaces.RoleDao;
import com.golosov.entities.Role;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
public class RoleDaoImplTest {

    @Autowired
    private RoleDao roleDao;

    private Role actualRole;
    private Role expectedRole;

    @Before
    public void setUp() {
        actualRole = new RoleBuilder
                .RoleEntityBuilder()
                .role("VIP")
                .build();
    }


    @Test
    public void testSave() {
        long id = roleDao.save(actualRole);

        expectedRole = roleDao.getById(id);
        Assert.assertEquals("testSave() method failed: ", actualRole, expectedRole);
    }

    @Test
    public void testDelete() {
        long id = roleDao.save(actualRole);

        roleDao.delete((long) id);

        expectedRole = roleDao.getById(id);
        Assert.assertNull("testSave() method failed: ", expectedRole);
    }

    @Test
    public void testUpdate() {
        long id = roleDao.save(actualRole);

        actualRole.setRole("xxxVIP");
        roleDao.update(actualRole);

        expectedRole = roleDao.getById(id);
        Assert.assertEquals("testSave() method failed: ", actualRole, expectedRole);
    }


    @Test
    public void testGetAll() {
        roleDao.save(actualRole);

        Role role = new RoleBuilder
                .RoleEntityBuilder()
                .role("VIP")
                .build();
        roleDao.save(role);

        List<Role> roles = roleDao.getAll();
        Assert.assertTrue("testSave() method failed: ", roles.size()>=2);
    }

    @Test
    public void testGetById() {
        long id = roleDao.save(actualRole);

        expectedRole = roleDao.getById(id);
        Assert.assertEquals("testGetById() method failed: ", actualRole, expectedRole);
    }

    @After
    public void dropDown() {
        actualRole = null;
        expectedRole = null;
    }
}
