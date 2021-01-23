package com.app.service.jms;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

@Component
public class MessageReceiver {
	
	private final MessageProducer messageProducer;

	@Autowired
	public MessageReceiver(MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	@JmsListener(destination = "inbound.queue")
	public void receiveMessage(final Message<JmsMessage> message) throws JMSException {
		JmsMessage payload = message.getPayload();
		messageProducer.sendMessage("outbound.queue", payload);
	}

}