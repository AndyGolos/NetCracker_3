package com.Golosov.daoTests;

import com.Golosov.builders.RoleBuilder;
import com.Golosov.dao.interfaces.RoleDao;
import com.Golosov.entities.Role;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
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
        expectedRole = roleDao.getById((long) id);
        Assert.assertEquals("testSave() method failed: ", actualRole, expectedRole);
        roleDao.delete((long) id);
    }

    @Test
    public void testDelete() {
        long id = roleDao.save(actualRole);
        roleDao.delete((long) id);
        expectedRole = roleDao.getById((long) id);
        Assert.assertNull("testSave() method failed: ", expectedRole);
    }

    @Test
    public void testUpdate() {
        long id = roleDao.save(actualRole);
        actualRole.setRole("xxxVIP");
        roleDao.update(actualRole);
        expectedRole = roleDao.getById((long) id);
        Assert.assertEquals("testSave() method failed: ", actualRole, expectedRole);
        roleDao.delete((long) id);
    }

    @Ignore
    @Test
    public void testGetAll() {

    }

    @Ignore
    @Test
    public void testGetRolesByUserId() {
        long id = roleDao.save(actualRole);
//      userRoles = roleDao.getRolesById();
    }

    @After
    public void dropDown() {
        actualRole = null;
        expectedRole = null;
    }
}
