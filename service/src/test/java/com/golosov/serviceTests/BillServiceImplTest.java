package com.golosov.serviceTests;

import com.golosov.services.dto.dto.BillDto;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.interfaces.BillService;
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
@Rollback
public class BillServiceImplTest {

    @Autowired
    private BillService billService;

    private BillDto actualBillDto;
    private BillDto expectedBillDto;

    @Before
    public void setUp() {
        actualBillDto = new BillDto();
        actualBillDto.setPassword("Hello");
        actualBillDto.setMoney(1000);
    }

    @Test
    public void testSave() {
        long id = billService.save(actualBillDto);

        expectedBillDto = billService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualBillDto, expectedBillDto);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete() {
        long id = billService.save(actualBillDto);

        billService.delete(id);

        expectedBillDto = billService.get(id);
        Assert.assertNull("testDelete() method failed: ", expectedBillDto);
    }

    @Test
    public void testUpdate() {
        long id = billService.save(actualBillDto);

        actualBillDto.setId(id);
        actualBillDto.setMoney(1000000);
        billService.update(actualBillDto);

        expectedBillDto = billService.get(id);
        Assert.assertEquals("testUpdate() method failed: ", actualBillDto, expectedBillDto);
    }

    @Test
    public void testGetAll() {
        long id = billService.save(actualBillDto);

        actualBillDto.setMoney(200);
        billService.save(actualBillDto);

        List<BillDto> bills = billService.getAll();
        Assert.assertTrue("testGetAll() method failed: ", bills.size() >= 2);
    }

    @Test
    public void testGetById() {
        long id = billService.save(actualBillDto);

        expectedBillDto = billService.get(id);
        Assert.assertEquals("testGetById() method failed: ", actualBillDto, expectedBillDto);
    }

    @Ignore
    @Test
    public void testReplenish() {

    }

    @After
    public void dropDown() {
        actualBillDto = null;
        expectedBillDto = null;
    }
}
