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

	public void sendMessage(final String queueName, final String jmsMessage) {
		jmsTemplate.send(queueName, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				message.setText(jmsMessage);
				return message;
			}
		});
	}

}