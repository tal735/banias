package com.app.service.email;

import com.app.service.jms.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class EmailDispatcherImpl implements EmailDispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailDispatcherImpl.class);

    private final EmailService emailService;
    private final TaskExecutor threadPoolTaskExecutor;

    public EmailDispatcherImpl(@Qualifier("SMTPEmailServiceImpl") EmailService emailService, TaskExecutor threadPoolTaskExecutor) {
        this.emailService = emailService;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @Override
    public void dispatch(EmailMessage emailMessage) {
        threadPoolTaskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    emailService.sendEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getText());
                } catch (Exception e) {
                    LOGGER.error("Exception while sending mail async", e);
                }
            }
        });
    }
}
