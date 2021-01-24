package com.app.config.mvc;

import org.springframework.context.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan(basePackages = {"com.app"})
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("my@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("*").allowCredentials(true).allowedHeaders("*").allowedMethods("*").allowedOrigins("http://localhost:4200");
//    }

//    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
//        return registry.addResourceHandler(RESOURCE_PATHS);
//    }
//
//    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
//        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
//    }
//
//    protected CacheControl getCacheControl() {
//        return CacheControl.maxAge(10, TimeUnit.DAYS).cachePublic();
//    }

}