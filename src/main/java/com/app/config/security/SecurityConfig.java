package com.app.config.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
@EnableWebSecurity
@ComponentScan(basePackages = "com.app")
public class SecurityConfig {
}