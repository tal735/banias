package com.app.config.smtp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class SMTPConfig {

    @Value("${smtp.host}")
    private String SMTP_HOST;

    @Value("${smtp.port}")
    private Integer SMTP_PORT;

    @Value("${smtp.user}")
    private String SMTP_USERNAME;

    @Value("${smtp.password}")
    private String SMTP_PASSWORD;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SMTP_HOST);
        mailSender.setPort(SMTP_PORT);

        mailSender.setUsername(SMTP_USERNAME);
        mailSender.setPassword(SMTP_PASSWORD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
