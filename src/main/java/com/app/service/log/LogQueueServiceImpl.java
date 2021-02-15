package com.app.service.log;

import com.app.service.email.EmailQueueServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class LogQueueServiceImpl implements LogQueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailQueueServiceImpl.class);

    public final static String QUEUE_NAME = "log.queue";

    private final BookingLogService bookingLogService;
    private final TaskExecutor threadPoolTaskExecutor;
    private final JmsTemplate jmsTemplate;

    public LogQueueServiceImpl(BookingLogService bookingLogService, @Qualifier("BookingLogThreadPoolTaskExecutor") TaskExecutor threadPoolTaskExecutor, JmsTemplate jmsTemplate) {
        this.bookingLogService = bookingLogService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void enqueue(final LogMessage logMessage) {
        jmsTemplate.send(QUEUE_NAME, session -> session.createObjectMessage(logMessage));
    }

    @JmsListener(destination = QUEUE_NAME)
    public void dequeue(final Message<LogMessage> logMessage) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                LogMessage log = logMessage.getPayload();
                bookingLogService.log(log);
            } catch (Exception e) {
                LOGGER.error("Exception while sending log", e);
            }
        });
    }

}
