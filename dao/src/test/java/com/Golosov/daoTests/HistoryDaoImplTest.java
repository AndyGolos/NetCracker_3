package com.Golosov.daoTests;

import com.Golosov.builders.*;
import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.dao.interfaces.CardDao;
import com.Golosov.dao.interfaces.HistoryDao;
import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class HistoryDaoImplTest {

    @Autowired
    private CardDao cardDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BillDao billDao;
    @Autowired
    private HistoryDao historyDao;

    private Type type;
    private User user;
    private Bill bill;
    private Card card;
    private History actualHistory;
    private History expectedHistory;

    @Before
    public void setUp() {
        type = new TypeBuilder
                .TypeEntityBuilder()
                .id(2)
                .build();

        bill = new BillBuilder
                .BillEntityBuilder()
                .password("1111")
                .build();

        user = new UserBuilder
                .UserEntityBuilder()
                .name("Andy")
                .surname("Golosov")
                .lastname("Dmitrievich")
                .email("qwerty@mail.ru")
                .password("creator")
                .dateOfBirth(LocalDate.now().minusYears(20))
                .registration(LocalDate.now())
                .build();

        card = new CardBuilder
                .CardEntityBuilder()
                .password("qwerty")
                .registration(LocalDate.now())
                .validity(LocalDate.now().plusYears(5))
                .build();

        card.setBill(bill);
        card.setType(type);
        card.setUser(user);

        actualHistory = new HistoryBuilder
                .HistoryEntityBuilder()
                .operationTime(Calendar.getInstance())
                .valueChange("+200")
                .card(card)
                .build();
    }


    @Test
    public void testSave() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(card);
        long historyId = saveHistory(actualHistory);

        expectedHistory = historyDao.getById(historyId);

        Assert.assertEquals("testSave() method failed: ", actualHistory, expectedHistory);

        delete(historyId, cardId, userId, billId);
    }

    @Test
    public void testDelete() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(card);
        long historyId = saveHistory(actualHistory);

        historyDao.delete(historyId);
        expectedHistory = historyDao.getById(historyId);

        Assert.assertNull("testDelete() method failed: ", expectedHistory);

        deleteCard(cardId);
        deleteBill(billId);
        deleteUser(userId);
    }

    @Test
    public void testUpdate() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(card);
        long historyId = saveHistory(actualHistory);

        actualHistory.setValueChange("-300");
        historyDao.save(actualHistory);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertEquals("testUpdate() method failed: ", actualHistory, expectedHistory);

        delete(historyId, cardId, userId, billId);
    }

    @Test
    public void testGetById() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(card);
        long historyId = saveHistory(actualHistory);

        expectedHistory = historyDao.getById(historyId);

        Assert.assertEquals("testGetById() method failed: ", actualHistory, expectedHistory);

        delete(historyId, cardId, userId, billId);
    }

    @Ignore
    @Test
    public void testGetAll(){

    }

    @Ignore
    @Test
    public void testGetHistoriesById(){

    }

    @After
    public void dropDown() {
        actualHistory = null;
        expectedHistory = null;
        bill = null;
        user = null;
        type = null;
        card = null;
    }

    private long saveBill(Bill bill) {
        return billDao.save(bill);
    }

    private long saveUser(User user) {
        return userDao.save(user);
    }

    private long saveCard(Card card) {
        return cardDao.save(card);
    }

    private long saveHistory(History history) {
        return historyDao.save(history);
    }

    private void deleteCard(long cardId) {
        cardDao.delete(cardId);
    }

    private void deleteUser(long userId) {
        userDao.delete(userId);
    }

    private void deleteBill(long billId) {
        billDao.delete(billId);
    }

    private void deleteHistory(long historyId) {
        historyDao.delete(historyId);
    }

    private void delete(long historyId, long cardId, long userId, long billId) {
        deleteHistory(historyId);
        deleteCard(cardId);
        deleteUser(userId);
        deleteBill(billId);
    }




}
