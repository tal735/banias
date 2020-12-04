package com.app.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext-test.xml"})
//@Transactional
public class HelloWorldTest {

    @Test
    public void test() {
        System.out.println(123);
    }
}