package com.app.service.email;

import com.app.service.jms.EmailMessage;

public interface EmailDispatcher {
    void dispatch(EmailMessage emailMessage);
}
