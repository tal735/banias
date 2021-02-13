package com.app.service.email;

public interface EmailQueueService {
    void enqueue(final EmailMessage emailMessage);
}
