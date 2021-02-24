package com.app.service.email;

import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:**/applicationContext-test.xml"})
@Ignore
public class SendGridEmailServiceImplTest extends TestCase {

    EmailService emailService;

    @Before
    public void init() {
        this.emailService = new SendGridEmailServiceImpl();
    }

    @Test
    public void testEmailSend() {
        List<String> to = Lists.newArrayList("test@example.com");
        String subject = "Sending with SendGrid is Fun";
        String text = "and easy to do anywhere, even with Java";
        emailService.sendEmail(to, subject, text);
    }
}