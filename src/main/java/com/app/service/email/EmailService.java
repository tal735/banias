package com.app.service.email;

public interface EmailService {
    boolean sendEmail(String to, String subject, String text);
}
