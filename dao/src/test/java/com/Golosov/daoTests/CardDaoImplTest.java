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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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
    public void testSave() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testSave() method failed: ", actualCard, expectedCard);

        delete(cardId, userId, billId);
    }

    @Test
    public void testDelete() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.delete(cardId);
        expectedCard = cardDao.getById(cardId);
        Assert.assertNull("testDelete() method failed: ", expectedCard);

        deleteUser(userId);
        deleteBill(billId);
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

        delete(cardId,userId,billId);
    }

    @Test
    public void testGetById() {

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertEquals("testUpdate() method failed: ", actualCard, expectedCard);

        delete(cardId,userId,billId);
    }

    @Ignore
    @Test
    public void testGetAllCardsByUserId(){

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        bill = new BillBuilder
                .BillEntityBuilder()
                .id(billId)
                .build();

        user = new UserBuilder
                .UserEntityBuilder()
                .id(userId)
                .build();

        actualCard.setUser(user);
        actualCard.setBill(bill);
    }

    @Ignore
    @Test
    public void testGetAll(){

    }

    @Test
    public void testBlockCard(){

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.blockCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertFalse("testBlockCard() method failed: ", expectedCard.isStatus());

        delete(cardId,userId,billId);
    }

    @Test
    public void testUnblockCard(){

        long billId = saveBill(bill);
        long userId = saveUser(user);
        long cardId = saveCard(actualCard);

        cardDao.unblockCard(actualCard);

        expectedCard = cardDao.getById(cardId);
        Assert.assertTrue("testUnblockCard() method failed: ", expectedCard.isStatus());

        delete(cardId,userId,billId);
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

    private void deleteCard(long cardId) {
        cardDao.delete(cardId);
    }

    private void deleteUser(long userId) {
        userDao.delete(userId);
    }

    private void deleteBill(long billId) {
        billDao.delete(billId);
    }

    private void delete(long cardId, long userId, long billId) {
        deleteCard(cardId);
        deleteUser(userId);
        deleteBill(billId);
    }

}
