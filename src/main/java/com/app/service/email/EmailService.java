package com.app.service.email;

import com.google.common.collect.Lists;

import java.util.List;

public interface EmailService {
    String EMAIL_FROM = "no-reply@vaniascamping.com";
    List<String> ADMIN_MAILING_LIST = Lists.newArrayList("admin@vaniascamping.com");

    boolean sendEmail(List<String> to, String subject, String text);
}
