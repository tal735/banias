package com.app.config.jms;

import com.google.common.collect.Lists;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfig {
    private static final String BROKER_URL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final String BROKER_USERNAME = "test";
    private static final String BROKER_PASSWORD = "test";

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setPassword(BROKER_USERNAME);
        connectionFactory.setUserName(BROKER_PASSWORD);
        connectionFactory.setTrustedPackages(Lists.newArrayList("com.app.service.jms"));
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
//        template.setDefaultDestinationName(QUEUE_NAME);
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("1-1");
        return factory;
    }
}
