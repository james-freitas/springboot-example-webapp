package com.leanstack.ws.service;

import com.leanstack.ws.AbstractTest;
import com.leanstack.ws.model.Greeting;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
public class GreetingServiceTest extends AbstractTest{

    @Autowired
    private GreetingService service;

    @Before
    public void setUp() {
        service.evictCache();
    }

    @After
    public void tearDown() {
        // clean up after each test method
    }

    @Test
    public void testFindAll() {
        Collection<Greeting> list = service.findAll();
        Assert.assertNotNull("failure - expected not null", list);
        Assert.assertEquals("failure - expected size", 2, list.size());
    }

    @Test
    public void testFindOne() {
        Long id = new Long(1);
        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected not null", entity);
        Assert.assertEquals("failure - expected id attribute match", id, entity.getId());
    }

    @Test
    public void testFindOneNotFound(){
        Long id = Long.MAX_VALUE;

        Greeting entity = service.findOne(id);

        Assert.assertNotNull("failure - expected null", entity);
    }


    @Test
    public void testCreate() {

        Greeting entity = new Greeting();
        entity.setText("text");

        Greeting createdEntity = service.create(entity);

        Assert.assertNotNull("failure - expected not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", "test", createdEntity.getText());

        Collection<Greeting> list = service.findAll();

        Assert.assertEquals("failure - expected size", 3, list.size());
    }



}
