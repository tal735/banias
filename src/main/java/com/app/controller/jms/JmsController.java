package com.app.controller.jms;

import com.app.service.jms.JmsMessage;
import com.app.service.jms.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JmsController {

    static final Logger LOG = LoggerFactory.getLogger(JmsController.class);

    private final MessageProducer messageProducer;

    @Autowired
    public JmsController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @GetMapping("/jms/send")
    @ResponseBody
    private void sendJms2() {
        JmsMessage jmsMessage = new JmsMessage();
        jmsMessage.setEmail("test@email.com");
        messageProducer.sendMessage("inbound.queue", jmsMessage);
    }
}
