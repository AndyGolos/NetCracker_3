package com.Golosov.serviceTests;

import com.Golosov.builders.TypeBuilder;
import com.Golosov.entities.Type;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.interfaces.TypeService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Андрей on 17.05.2017.
 */
@Transactional
@ContextConfiguration("/serviceContextTest.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TypeServiceImplTest {

    @Autowired
    private TypeService typeService;

    private Type actualType;
    private Type expectedType;
    private TypeDto actualTypeDto;
    private TypeDto expectedTypeDto;

    @Before
    public void setUp() {

        actualTypeDto = new TypeDto();
        actualTypeDto.setType("SuperCard");


        /*actualType = new TypeBuilder
                .TypeEntityBuilder()
                .type("SuperCard")
                .build();*/
    }

    @Test
    public void testSave() {
        long id = typeService.save(actualTypeDto);

        expectedTypeDto = typeService.get(id);
        Assert.assertEquals("testSave() method failed: ", actualTypeDto, expectedTypeDto);

        typeService.delete(id);
    }

    @After
    public void dropDown() {
        actualType = null;
        expectedType = null;
    }
}
