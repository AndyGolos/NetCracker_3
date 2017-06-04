package com.golosov.serviceTests;

import com.golosov.services.dto.dto.*;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.interfaces.BillService;
import com.golosov.services.interfaces.CardService;
import com.golosov.services.interfaces.TypeService;
import com.golosov.services.interfaces.UserService;
import org.junit.*;
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
@Rollback
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

        String validity = localDateToStringConverter(LocalDate.now().plusYears(5));
        String registration = localDateToStringConverter(LocalDate.now());

        actualCardDto = new CardDto();
        actualCardDto.setActive(true);
        actualCardDto.setPassword("1234");
        actualCardDto.setValidity(validity);
        actualCardDto.setRegistration(registration);

        userDto = new UserDto();
        userDto.setName("Andy");
        userDto.setSurname("golosov");
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
    public void testUpdate() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        actualCardDto.setPassword("LOCALHOST8080");
        cardService.update(actualCardDto, cardId);

        expectedCardDto = cardService.get(cardId);
        Assert.assertEquals("testUpdate() method failed: ", actualCardDto, expectedCardDto);
    }

    @Test(expected = NotFoundException.class)
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

    @Test
    public void testTransferMoney() {
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        actualCardDto.setUserId(userId);
        actualCardDto.setBillId(billId);
        actualCardDto.setType(typeId);
        long cardId = cardService.save(actualCardDto);

        BillDto billDto1 = new BillDto();
        billDto1.setMoney(billDto.getMoney());
        billDto1.setPassword("qqq");
        long billtransferId = billService.save(billDto1);

        actualCardDto.setBillId(billtransferId);
        long cardTransferId = cardService.save(actualCardDto);

        TransferDto transferDto = new TransferDto(cardId, actualCardDto.getPassword(), cardTransferId, 300);

        boolean result = cardService.transferMoney(transferDto);
        Assert.assertTrue("testTransferMoney() method failed: ", result);

        BillDto fromBill  = billService.get(billId);
        BillDto toBill = billService.get(billtransferId);
        Assert.assertTrue("testTransferMoney() method failed: ", fromBill.getMoney() + toBill.getMoney() == 2000);
    }

    @After
    public void dropDown() {
        actualCardDto = null;
        expectedCardDto = null;
    }

    private String localDateToStringConverter(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (localDate != null) {
            return localDate.format(formatter);
        } else
            return null;
    }
}
