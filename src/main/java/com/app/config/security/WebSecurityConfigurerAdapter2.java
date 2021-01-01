package com.app.config.security;

import com.app.service.booking.BookingService;
import com.app.service.otp.OTPService;
import com.app.service.otp.OTPServiceImpl;
import com.app.service.user.booker.BookerUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfigurerAdapter2 extends WebSecurityConfigurerAdapter {

    @Bean
    public OTPService otpService() {
        return new OTPServiceImpl();
    }

    @Autowired
    BookingService bookingService;

    @Bean
    public AccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestUnauthorizedEntryPoint();
    }

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }


    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService otpUserDetailsService() {
        return new BookerUserDetailsServiceImpl(bookingService, otpService());
    }

    @Bean
    public AuthenticationProvider otpAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(otpUserDetailsService());
        provider.setPasswordEncoder(noOpPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(otpAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api/booking/**").and()
                .cors().configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.addAllowedOrigin("http://localhost:4200");
                config.setAllowCredentials(true);
                return config;
            }
        }).and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .accessDeniedHandler(restAccessDeniedHandler())
                .and()
                .formLogin()
                .loginProcessingUrl("/otp/auth")
                .usernameParameter("reference")
                .passwordParameter("otp")
                .successHandler(ajaxAuthenticationSuccessHandler())
                .failureHandler(ajaxAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/otp/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler())
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/otp/request").permitAll()
                .anyRequest().hasRole("OTP");
    }
}
