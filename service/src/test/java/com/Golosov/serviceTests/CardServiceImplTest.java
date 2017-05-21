package com.Golosov.serviceTests;

import com.Golosov.services.dto.dto.BillDto;
import com.Golosov.services.dto.dto.CardDto;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.dto.dto.UserDto;
import com.Golosov.services.interfaces.BillService;
import com.Golosov.services.interfaces.CardService;
import com.Golosov.services.interfaces.TypeService;
import com.Golosov.services.interfaces.UserService;
import org.junit.*;
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
public class CardServiceImplTest {

    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private BillService billService;

    private CardDto actualCardDto;
    private CardDto expectedCardDto;

    private UserDto userDto;
    private BillDto billDto;
    private TypeDto typeDto;

    @Before
    public void setUp() {
        actualCardDto = new CardDto();
        actualCardDto.setActive(true);
        actualCardDto.setPassword("1234");
        actualCardDto.setValidity("22.12.2015");
        actualCardDto.setRegistration("22.12.2015");

        userDto = new UserDto();
        userDto.setName("Andy");
        userDto.setSurname("Golosov");
        userDto.setLastname("Dmitrievich");
        userDto.setPassword("1234567890");
        userDto.setEmail("AndyGolos@mail.ru");
        userDto.setBirth("22.12.2016");
        userDto.setRegistration("22.12.2017");

        billDto = new BillDto();
        billDto.setPassword("Hello");
        billDto.setMoney(1000);

        typeDto = new TypeDto();
        typeDto.setType("SuperCard");
    }

    @Test
    @Rollback
    public void testSave() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        expectedCardDto = cardService.get(cardId);
        Assert.assertEquals("testSave() method failed: ", actualCardDto, expectedCardDto);
    }

    @Test
    @Rollback
    public void testUpdate() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        actualCardDto.setId(cardId);
        actualCardDto.setPassword("LOCALHOST8080");
        cardService.update(actualCardDto);

        expectedCardDto = cardService.get(cardId);
        Assert.assertEquals("testUpdate() method failed: ", actualCardDto, expectedCardDto);
    }

    @Test
    @Rollback
    public void testDelete() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        cardService.delete(cardId);

        expectedCardDto = cardService.get(cardId);
        Assert.assertNull("testDelete() method failed: ", expectedCardDto);
    }

    @Test
    @Rollback
    public void testGetAll() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId1 = cardService.save(actualCardDto);
        actualCardDto.setType(2);
        cardService.save(actualCardDto);

        List<CardDto> cards = cardService.getAll();
        Assert.assertTrue("testGetAll() method failed: ", cards.size() >= 2);
    }

    @Test
    @Rollback
    public void testGetById() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        expectedCardDto = cardService.get(cardId);
        Assert.assertEquals("testGetById() method failed: ", actualCardDto, expectedCardDto);
    }

    //TODO поправить тест
    @Test
    @Rollback
    public void testReplenish() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        BillDto billDto1 = new BillDto();
        billDto1.setMoney(0);
        billDto1.setPassword("qqq");
        long billtransferId = billService.save(billDto1);

        actualCardDto.setBillId(billtransferId);
        long cardTransferId = cardService.save(actualCardDto);

        boolean result = cardService.replenishCard(cardId, actualCardDto.getPassword(), cardTransferId, 300);
        Assert.assertTrue("testReplenish() method failed: ", result);

        BillDto bill1 = billService.get(billId);
        BillDto bill2 = billService.get(billtransferId);
        Assert.assertTrue("testReplenish() method failed: ", bill1.getMoney() + bill2.getMoney() == 1000);
    }

    @After
    public void dropDown() {
        actualCardDto = null;
        expectedCardDto = null;
    }
}
