package com.app.service.email;

import java.util.List;

public interface EmailService {
    boolean sendEmail(List<String> to, String subject, String text);
}
