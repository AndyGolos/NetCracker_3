package com.Golosov.serviceTests;

import com.Golosov.services.dto.dto.RoleDto;
import com.Golosov.services.interfaces.RoleService;
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

import java.util.List;

/**
 * Created by Андрей on 20.05.2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/serviceContextTest.xml")
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    private RoleDto actualRoleDto;
    private RoleDto expectedRoleDto;

    @Before
    public void setUp() {
        actualRoleDto = new RoleDto();
        actualRoleDto.setRole("VIP");
    }

    @Test
    @Rollback
    public void testSave() {
        long id = roleService.save(actualRoleDto);

        expectedRoleDto = roleService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualRoleDto, expectedRoleDto);
    }

    @Test
    @Rollback
    public void testDelete() {
        long id = roleService.save(actualRoleDto);

        roleService.delete(id);

        expectedRoleDto = roleService.get(id);
        Assert.assertNull("testDelete() method failed: ", expectedRoleDto);
    }

    @Test
    @Rollback
    public void testUpdate() {
        long id = roleService.save(actualRoleDto);

        actualRoleDto.setId(id);
        actualRoleDto.setRole("SuperVip");
        roleService.update(actualRoleDto);

        expectedRoleDto = roleService.get(id);
        Assert.assertEquals("testUpdate() method failed: ", actualRoleDto, expectedRoleDto);
    }

    @Test
    @Rollback
    public void testGetById() {
        long id = roleService.save(actualRoleDto);

        expectedRoleDto = roleService.get(id);
        Assert.assertEquals("testGetById() method failed: ", actualRoleDto, expectedRoleDto);
    }

    @Test
    @Rollback
    public void testGetAll() {
        long id = roleService.save(actualRoleDto);

        actualRoleDto.setRole("VIP");
        long id2 = roleService.save(actualRoleDto);

        List<RoleDto> roles = roleService.getAll();
        Assert.assertTrue("testGetAll() method failed: ",roles.size()>=2);
    }


    @After
    public void dropDown() {
        actualRoleDto = null;
        expectedRoleDto = null;
    }
}
