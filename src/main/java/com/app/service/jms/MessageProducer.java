package com.app.service.jms;

import javax.jms.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

	public final static String OTP_EMAIL_QUEUE_NAME = "inbound.queue";

	private final JmsTemplate jmsTemplate;

	@Autowired
	public MessageProducer(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final String queueName, final EmailMessage emailMessage) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage(emailMessage);
				return objectMessage;
			}
		});
	}

}