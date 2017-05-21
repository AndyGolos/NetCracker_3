package com.Golosov.daoTests;

import com.Golosov.builders.BillBuilder;
import com.Golosov.builders.CardBuilder;
import com.Golosov.builders.TypeBuilder;
import com.Golosov.builders.UserBuilder;
import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.dao.interfaces.CardDao;
import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.Bill;
import com.Golosov.entities.Card;
import com.Golosov.entities.Type;
import com.Golosov.entities.User;
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
                .surname("Golosov")
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
    @Rollback
    public void testSave() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testSave() method failed: ", actualCard, expectedCard);
    }

    @Test
    @Rollback
    public void testDelete() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.delete(cardId);
        expectedCard = cardDao.getById(cardId);
        Assert.assertNull("testDelete() method failed: ", expectedCard);
    }

    @Test
    @Rollback
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
    @Rollback
    public void testGetById() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testUpdate() method failed: ", actualCard, expectedCard);
    }

    //TODO метод не пашет
    @Ignore
    @Test
    @Rollback
    public void testGetAllCardsByUserId(){
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
        long id = saveUser(user);
        saveCard(card);

        Set<Card> cards = cardDao.getAllCardsByUserId(id);
        Assert.assertTrue("testGetAllCardsByUserId() method failed: ",cards.size()>=2);
    }

    @Test
    @Rollback
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
    @Rollback
    public void testBlockCard(){

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.blockCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertFalse("testBlockCard() method failed: ", expectedCard.isStatus());
    }

    @Test
    @Rollback
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
