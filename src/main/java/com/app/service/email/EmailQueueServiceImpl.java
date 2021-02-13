package com.app.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class EmailQueueServiceImpl implements EmailQueueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailQueueServiceImpl.class);

    public final static String EMAIL_QUEUE_NAME = "inbound.queue";

    private final EmailService emailService;
    private final TaskExecutor threadPoolTaskExecutor;
    private final JmsTemplate jmsTemplate;

    public EmailQueueServiceImpl(@Qualifier("SMTPEmailServiceImpl") EmailService emailService, TaskExecutor threadPoolTaskExecutor, JmsTemplate jmsTemplate) {
        this.emailService = emailService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void enqueue(final EmailMessage emailMessage) {
        jmsTemplate.send(EMAIL_QUEUE_NAME, session -> session.createObjectMessage(emailMessage));
    }

    @JmsListener(destination = EMAIL_QUEUE_NAME)
    public void dequeue(final Message<EmailMessage> message) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                EmailMessage emailMessage = message.getPayload();
                emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getText());
            } catch (Exception e) {
                LOGGER.error("Exception while sending mail async", e);
            }
        });
    }
}
