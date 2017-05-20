package com.Golosov.daoTests;

import com.Golosov.builders.TypeBuilder;
import com.Golosov.dao.interfaces.TypeDao;
import com.Golosov.entities.Type;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional(rollbackFor = Exception.class)
@ContextConfiguration("/daoContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TypeDaoImplTest {

    @Autowired
    private TypeDao typeDao;

    private Type actualType;
    private Type expectedType;

    @Before
    public void setUp() {
        actualType = new TypeBuilder
                .TypeEntityBuilder()
                .type("SuperCard")
                .build();
    }


    @Test
    public void testSave() {
        long id = typeDao.save(actualType);

        expectedType = typeDao.getById(id);
        Assert.assertEquals("testSave() method failed: ", actualType, expectedType);

        typeDao.delete(id);
    }

    @Test
    public void testDelete() {
        long id = typeDao.save(actualType);

        typeDao.delete(id);

        expectedType = typeDao.getById(id);
        Assert.assertNull("testDelete() method failed: ", expectedType);
    }

    @Test
    public void testUpdate() {
        long id = typeDao.save(actualType);

        actualType.setType("SUPERSUPERSUPERCARD");
        typeDao.update(actualType);

        expectedType = typeDao.getById(id);
        Assert.assertEquals("testUpdate() method failed: ", actualType, expectedType);

        typeDao.delete(id);
    }

    @Test
    public void testGetById() {
        long id = typeDao.save(actualType);

        expectedType = typeDao.getById(id);
        Assert.assertEquals("testGetById() method failed: ", actualType, expectedType);

        typeDao.delete(id);
    }

    @Ignore
    @Test
    public void testGetAll() {

    }


    @After
    public void dropDown() {
        actualType = null;
        expectedType = null;
    }

}
