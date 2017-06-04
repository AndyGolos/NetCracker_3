package com.golosov.daoTests;

import com.golosov.builders.*;
import com.golosov.dao.interfaces.BillDao;
import com.golosov.dao.interfaces.CardDao;
import com.golosov.dao.interfaces.HistoryDao;
import com.golosov.dao.interfaces.UserDao;
import com.golosov.entities.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
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
                .surname("golosov")
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
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertEquals("testSave() method failed: ", actualHistory, expectedHistory);
    }

    @Test
    public void testDelete() {
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

        historyDao.delete(historyId);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertNull("testDelete() method failed: ", expectedHistory);
    }

    @Test
    public void testUpdate() {
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

        actualHistory.setValueChange("-300");
        historyDao.save(actualHistory);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertEquals("testUpdate() method failed: ", actualHistory, expectedHistory);
    }

    @Test
    public void testGetById() {
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertEquals("testGetById() method failed: ", actualHistory, expectedHistory);
    }

    @Test
    public void testGetAll(){
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        saveHistory(actualHistory);

        History history = new HistoryBuilder
                .HistoryEntityBuilder()
                .operationTime(Calendar.getInstance())
                .valueChange("+4400")
                .card(card)
                .build();
        historyDao.save(history);

        List<History> histories = historyDao.getAll();
        Assert.assertTrue("testGetById() method failed: ",histories.size()>=2);
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
}
