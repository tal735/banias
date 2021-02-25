package com.app.controller.validator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext-test.xml"})
public class EmailValidatorTest extends TestCase {

    @Test
    public void shouldBeInvalid() {
        // given
        String email = "a@a";
        String email2 = "aa";
        String email3 = "";
        String email4 = "@gmail.com";
        String email5 = "gmail.com";
        String email6 = "user@.com";

        // when
        boolean valid = EmailValidator.isValid(email);
        boolean valid2 = EmailValidator.isValid(email2);
        boolean valid3 = EmailValidator.isValid(email3);
        boolean valid4 = EmailValidator.isValid(email4);
        boolean valid5 = EmailValidator.isValid(email5);
        boolean valid6 = EmailValidator.isValid(email6);

        // then
        Assert.assertFalse(valid);
        Assert.assertFalse(valid2);
        Assert.assertFalse(valid3);
        Assert.assertFalse(valid4);
        Assert.assertFalse(valid5);
        Assert.assertFalse(valid6);
    }

    @Test
    public void shouldBeValid() {
        // given
        String email = "user@domain.tld";
        String email2 = "user@sub.domain.tld";

        // when
        boolean valid = EmailValidator.isValid(email);
        boolean valid2 = EmailValidator.isValid(email2);

        // then
        Assert.assertTrue(valid);
        Assert.assertTrue(valid2);
    }

}