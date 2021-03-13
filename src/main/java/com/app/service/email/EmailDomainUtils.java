package com.app.service.email;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class EmailDomainUtils {

    public static String EMAIL_NOTIFICATION_FROM;
    public static List<String> ADMIN_MAILING_LIST;

    private static String domain;

    @PostConstruct
    public void postConstruct() {
        EMAIL_NOTIFICATION_FROM = getEmailWithDomain("no-reply");
        ADMIN_MAILING_LIST = Lists.newArrayList(getEmailWithDomain("admin"));
    }

    @Value("${app.domain}")
    public void setDomain(String domain) {
        EmailDomainUtils.domain = domain;
    }

    public static String getEmailWithDomain(String name) {
        return name + "@" + domain;
    }
}
