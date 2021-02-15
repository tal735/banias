package com.app.service.log;

public interface LogQueueService {
    void enqueue(final LogMessage emailMessage);
}
