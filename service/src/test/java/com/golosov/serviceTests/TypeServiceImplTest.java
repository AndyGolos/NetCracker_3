package com.golosov.serviceTests;

import com.golosov.services.dto.dto.TypeDto;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.interfaces.TypeService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/serviceContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Rollback
public class TypeServiceImplTest {

    @Autowired
    private TypeService typeService;

    private TypeDto actualTypeDto;
    private TypeDto expectedTypeDto;


    @Before
    public void setUp() {
        actualTypeDto = new TypeDto();
        actualTypeDto.setType("SuperCard");
    }

    @Test
    public void testSave() {
        long id = typeService.save(actualTypeDto);

        expectedTypeDto = typeService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualTypeDto, expectedTypeDto);
    }

    @Test(expected = NotFoundException.class)
    public void testDelete(){
        long id = typeService.save(actualTypeDto);

        typeService.delete(id);

        expectedTypeDto = typeService.get(id);
        Assert.assertNull("testDelete() method failed: ",expectedTypeDto);
    }

    @Test
    public void testUpdate(){
        long id = typeService.save(actualTypeDto);

        actualTypeDto.setType("UnbeliveableSuperCard");
        typeService.update(actualTypeDto, id);

        expectedTypeDto = typeService.get(id);
        Assert.assertEquals("testUpdate() method failed: ",actualTypeDto, expectedTypeDto);
    }

    @Test
    public void testGetById(){
        long id = typeService.save(actualTypeDto);

        expectedTypeDto = typeService.get(id);
        Assert.assertEquals("testGetById() method failed: ",actualTypeDto, expectedTypeDto);
    }

    @Test
    public void testGetAll(){
        long id1 = typeService.save(actualTypeDto);
        long id2 = typeService.save(actualTypeDto);

        List<TypeDto> types = typeService.getAll();
        Assert.assertTrue("testGetAll() method failed: ", types.size()>=2);
    }

    @After
    public void dropDown() {
        expectedTypeDto = null;
        expectedTypeDto = null;
    }
}
