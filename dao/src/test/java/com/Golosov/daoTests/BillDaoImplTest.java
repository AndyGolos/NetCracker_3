package com.Golosov.daoTests;

import com.Golosov.builders.BillBuilder;
import com.Golosov.dao.interfaces.BillDao;
import com.Golosov.entities.Bill;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BillDaoImplTest {

    @Autowired
    private BillDao billDao;


    private Bill expectedBill;
    private Bill actualBill;

    @Before
    public void setUp() {
        actualBill = new BillBuilder
                .BillEntityBuilder()
                .money(2)
                .password("1111")
                .build();
    }

    @Test
    public void testSave() {
        long id = billDao.save(actualBill);

        expectedBill = billDao.getById(id);
        Assert.assertEquals("testSave() method failed: ", actualBill, expectedBill);

        billDao.delete(id);
    }

    @Test
    public void testDelete() {
        long id = billDao.save(actualBill);

        billDao.delete(id);

        expectedBill = billDao.getById(id);
        Assert.assertNull("testDelete() method failed: ", expectedBill);
    }

    @Test
    public void testUpdate() {
        long id = billDao.save(actualBill);

        actualBill.setMoney(2313);
        billDao.save(actualBill);

        expectedBill = billDao.getById(id);
        Assert.assertEquals("testUpdate() method failed: ", actualBill, expectedBill);

        billDao.delete(id);
    }

    @Test
    public void testGetById() {
        long id = billDao.save(actualBill);

        expectedBill = billDao.getById(id);
        Assert.assertEquals("testGetById() method failed: ", actualBill, expectedBill);

        billDao.delete(id);
    }

    @Ignore
    @Test
    public void testGetAll() {

    }


    @Test
    public void testSetMoney() {
        long id = billDao.save(actualBill);
        long money = 200;
        billDao.setMoney(actualBill, money);

        expectedBill = billDao.getById(id);
        Assert.assertEquals("testSetMoney() method failed: ", actualBill, expectedBill);

        billDao.delete(id);
    }

    @After
    public void dropDown() {
        actualBill = null;
        expectedBill = null;
    }
}
