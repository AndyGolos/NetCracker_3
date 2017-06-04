package com.golosov.daoTests;

import com.golosov.builders.BillBuilder;
import com.golosov.builders.CardBuilder;
import com.golosov.builders.TypeBuilder;
import com.golosov.builders.UserBuilder;
import com.golosov.dao.interfaces.BillDao;
import com.golosov.dao.interfaces.CardDao;
import com.golosov.dao.interfaces.UserDao;
import com.golosov.entities.Bill;
import com.golosov.entities.Card;
import com.golosov.entities.Type;
import com.golosov.entities.User;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
public class CardDaoImplTest {


    @Autowired
    private CardDao cardDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BillDao billDao;

    private Card expectedCard;
    private Card actualCard;
    private Type type;
    private Bill bill;
    private User user;

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

        actualCard = new CardBuilder
                .CardEntityBuilder()
                .password("qwerty")
                .registration(LocalDate.now())
                .validity(LocalDate.now().plusYears(5))
                .build();

        actualCard.setBill(bill);
        actualCard.setType(type);
        actualCard.setUser(user);
    }

    @Test
    public void testSave() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testSave() method failed: ", actualCard, expectedCard);
    }

    @Test
    public void testDelete() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.delete(cardId);
        expectedCard = cardDao.getById(cardId);
        Assert.assertNull("testDelete() method failed: ", expectedCard);
    }

    @Test
    public void testUpdate() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        actualCard.setPassword("1234567890");
        cardDao.update(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testUpdate() method failed: " , actualCard, expectedCard);
    }

    @Test
    public void testGetById() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testUpdate() method failed: ", actualCard, expectedCard);
    }

    @Test
    public void testGetAllCardsByUserId(){
        saveBill(bill);
        long id = saveUser(user);
        saveCard(actualCard);

        Card card = new CardBuilder
                .CardEntityBuilder()
                .password("1111111")
                .registration(LocalDate.now())
                .validity(LocalDate.now().plusYears(5))
                .bill(bill)
                .type(type)
                .user(user)
                .build();

        saveBill(bill);
        saveUser(user);
        saveCard(card);

        List<Card> cards = cardDao.getAllCardsByUserId(id);
        Assert.assertTrue("testGetAllCardsByUserId() method failed: ",cards.size()>=2);
    }

    @Test
    public void testGetAll(){
        saveBill(bill);
        saveUser(user);
        saveCard(actualCard);

        Card card = new CardBuilder
                .CardEntityBuilder()
                .password("1111111")
                .registration(LocalDate.now())
                .validity(LocalDate.now().plusYears(5))
                .bill(bill)
                .type(type)
                .user(user)
                .build();

        saveBill(bill);
        saveUser(user);
        saveCard(card);

        List<Card> cards = cardDao.getAll();
        Assert.assertTrue("testGetAll() method failed: ",cards.size()>=2);
    }

    @Test
    public void testBlockCard(){

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.blockCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertFalse("testBlockCard() method failed: ", expectedCard.isStatus());
    }

    @Test
    public void testUnblockCard(){

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.unblockCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertTrue("testUnblockCard() method failed: ", expectedCard.isStatus());
    }

    @After
    public void dropDown() {
        actualCard = null;
        expectedCard = null;
        user = null;
        bill = null;
        type = null;
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

}
