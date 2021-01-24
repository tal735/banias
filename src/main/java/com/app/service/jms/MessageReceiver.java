package com.app.service.jms;

import com.app.service.email.EmailDispatcher;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

@Component
public class MessageReceiver {

	private final EmailDispatcher emailDispatcher;

	@Autowired
	public MessageReceiver(EmailDispatcher emailDispatcher) {
		this.emailDispatcher = emailDispatcher;
	}

	@JmsListener(destination = MessageProducer.EMAIL_QUEUE_NAME)
	public void receiveMessage(final Message<EmailMessage> message) throws JMSException {
		emailDispatcher.dispatch(message.getPayload());
	}

}