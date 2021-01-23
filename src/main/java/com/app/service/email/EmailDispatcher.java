package com.app.service.email;

import com.app.service.jms.JmsMessage;

public interface EmailDispatcher {
    void dispatch(JmsMessage jmsMessage);
}
