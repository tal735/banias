package com.app.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service("SMTPEmailServiceImpl")
public class SMTPEmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMTPEmailServiceImpl.class);

    private final JavaMailSender emailSender;

    @Autowired
    public SMTPEmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public boolean sendEmail(String to, String subject, String text) {
        LOGGER.debug("Sending Email to: " + to + ", subject: " + subject + ", text: " + text);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@baniascamping.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        emailSender.send(message);
        return true;
    }
}
