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
    @Rollback
    public void testSave() {
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertEquals("testSave() method failed: ", actualHistory, expectedHistory);
    }

    @Test
    @Rollback
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
    @Rollback
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
    @Rollback
    public void testGetById() {
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

        expectedHistory = historyDao.getById(historyId);
        Assert.assertEquals("testGetById() method failed: ", actualHistory, expectedHistory);
    }

    @Test
    @Rollback
    public void testGetAll(){
        saveBill(bill);
        saveUser(user);
        saveCard(card);
        long historyId = saveHistory(actualHistory);

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

    //TODO
    @Ignore
    @Test
    @Rollback
    public void testGetHistoriesByCardId(){

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
