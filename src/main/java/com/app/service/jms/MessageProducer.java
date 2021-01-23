package com.app.service.jms;

import javax.jms.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

	private final JmsTemplate jmsTemplate;

	@Autowired
	public MessageProducer(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void sendMessage(final String queueName, final JmsMessage jmsMessage) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objectMessage = session.createObjectMessage(jmsMessage);
				return objectMessage;
			}
		});
	}

}