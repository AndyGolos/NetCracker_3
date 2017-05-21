package com.Golosov.serviceTests;

import com.Golosov.services.dto.dto.*;
import com.Golosov.services.interfaces.*;
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
public class HistoryServiceImplTest {

    @Autowired
    private HistoryService historyService;
    @Autowired
    private CardService cardService;
    @Autowired
    private UserService userService;
    @Autowired
    private BillService billService;
    @Autowired
    private TypeService typeService;


    private HistoryDto actualHistoryDto;
    private HistoryDto expectedHistoryDto;
    private BillDto billDto;
    private UserDto userDto;
    private TypeDto typeDto;
    private CardDto cardDto;


    @Before
    public void setUp() {
        cardDto = new CardDto();
        cardDto.setActive(true);
        cardDto.setPassword("1234");
        cardDto.setValidity("22.12.2015");
        cardDto.setRegistration("22.12.2015");

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

        actualHistoryDto = new HistoryDto();
        actualHistoryDto.setOperationTime("12.12.2016 08:12:12");
        actualHistoryDto.setValueChange("+200");
    }

    @Test
    @Rollback
    public void testSave() {
        long id = saveCardBillTypeUserAndHistory();

        expectedHistoryDto = historyService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualHistoryDto, expectedHistoryDto);
    }

    @Test
    @Rollback
    public void testDelete() {
        long id = saveCardBillTypeUserAndHistory();

        historyService.delete(id);

        expectedHistoryDto = historyService.get(id);
        Assert.assertNull("testDelete() method failed: ", expectedHistoryDto);
    }

    @Test
    @Rollback
    public void testUpdate() {
        long id = saveCardBillTypeUserAndHistory();

        actualHistoryDto.setId(id);
        actualHistoryDto.setValueChange("0");
        historyService.update(actualHistoryDto);

        expectedHistoryDto = historyService.get(id);
        Assert.assertEquals("testUpdate() method failed: ", actualHistoryDto, expectedHistoryDto);
    }

    @Test
    @Rollback
    public void testGetById() {
        long id = saveCardBillTypeUserAndHistory();

        expectedHistoryDto = historyService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualHistoryDto, expectedHistoryDto);
    }

    @Test
    @Rollback
    public void testGetAll() {
        saveCardBillTypeUserAndHistory();

        actualHistoryDto.setValueChange("+1000");
        historyService.save(actualHistoryDto);

        List<HistoryDto> histories = historyService.getAll();
        Assert.assertTrue("testGetAll() method failed: ", histories.size() >= 2);
    }

    @Ignore
    @Test
    @Rollback
    public void testFindCardHistory() {

    }

    @After
    public void dropDown() {
        actualHistoryDto = null;
        expectedHistoryDto = null;
    }

    private long saveCardBillTypeUserAndHistory(){
        long billId = billService.save(billDto);
        long typeId = typeService.save(typeDto);
        long userId = userService.save(userDto);

        cardDto.setUserId(userId);
        cardDto.setBillId(billId);
        cardDto.setType(typeId);
        long cardId = cardService.save(cardDto);
        actualHistoryDto.setCardId(cardId);
        return  historyService.save(actualHistoryDto);
    }
}
