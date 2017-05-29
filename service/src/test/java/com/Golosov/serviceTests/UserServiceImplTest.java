package com.Golosov.serviceTests;

import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.UserService;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Андрей on 20.05.2017.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/serviceContextTest.xml")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private UserDto actualUserDto;
    private UserDto expectedUserDto;

    @Before
    public void setUp() {
        String registration = localDateToStringConverter(LocalDate.now());
        actualUserDto = new UserDto();
        actualUserDto.setName("Andt");
        actualUserDto.setSurname("Golosov");
        actualUserDto.setLastname("Dmitrievich");
        actualUserDto.setPassword("1234567890");
        actualUserDto.setEmail("AndyGolos@mail.ru");
        actualUserDto.setBirth("22.12.2016");
        actualUserDto.setRegistration(registration);
    }

    @Test
    @Rollback
    public void testSave() {
        long id = userService.save(actualUserDto);

        expectedUserDto = userService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualUserDto, expectedUserDto);
    }

    @Test
    @Rollback
    public void testDelete() {
        long id = userService.save(actualUserDto);

        userService.delete(id);

        expectedUserDto = userService.get(id);
        Assert.assertNull("testDelete() method failed: ", expectedUserDto);
    }

    @Test
    @Rollback
    public void testUpdate() {
        long id = userService.save(actualUserDto);

        actualUserDto.setId(id);
        actualUserDto.setName("LOCALHOST8080");
        userService.update(actualUserDto);

        expectedUserDto = userService.get(id);
        Assert.assertEquals("testUpdate() method failed: ", actualUserDto, expectedUserDto);
    }

    @Test
    @Rollback
    public void testGetById() {
        long id = userService.save(actualUserDto);

        expectedUserDto = userService.get(id);
        Assert.assertEquals("testGetById() method failed: ", actualUserDto, expectedUserDto);
    }

    @Test
    @Rollback
    public void testGetAll() {
        long id = userService.save(actualUserDto);

        actualUserDto.setName("LOCALHOST8080");
        userService.save(actualUserDto);

        List<UserDto> users = userService.getAll();
        Assert.assertTrue("testGetById() method failed: ", users.size() >= 2);
    }


    @Test
    @Rollback
    public void testGetByEmail() {
        userService.save(actualUserDto);

        expectedUserDto = userService.findUserInfo(actualUserDto);
        Assert.assertEquals("testGetByEmail() method failed: ", actualUserDto, expectedUserDto);
    }

    @After
    public void dropDown() {
        actualUserDto = null;
        expectedUserDto = null;
    }

    private String localDateToStringConverter(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (localDate != null) {
            return localDate.format(formatter);
        } else
            return null;
    }
}
