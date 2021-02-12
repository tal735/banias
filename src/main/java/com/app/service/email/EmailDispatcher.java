package com.app.service.email;

public interface EmailDispatcher {
    void sendMessage(final EmailMessage emailMessage);
}
