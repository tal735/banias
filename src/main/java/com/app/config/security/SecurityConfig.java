package com.app.config.security;

import com.app.service.booking.BookingService;
import com.app.service.otp.OTPService;
import com.app.service.user.UserDetailsServiceImpl;
import com.app.service.user.UserService;
import com.app.service.user.OTPUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@EnableWebSecurity
@ComponentScan(basePackages = "com.app.config")
public class SecurityConfig  {

    @Autowired
    UserService userService;

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

    @Configuration
    public class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Bean
        public PasswordEncoder BCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService UserDetailsService() {
            return new UserDetailsServiceImpl(userService);
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setUserDetailsService(UserDetailsService());
            provider.setPasswordEncoder(BCryptPasswordEncoder());
            return provider;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) {
            auth.authenticationProvider(authenticationProvider());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
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
                    .loginProcessingUrl("/authentication")
                    .successHandler(ajaxAuthenticationSuccessHandler())
                    .failureHandler(ajaxAuthenticationFailureHandler())
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(ajaxLogoutSuccessHandler())
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/otp/**").permitAll()
                    .antMatchers("/user").permitAll()
                    .antMatchers("/admin/**").hasAuthority(AuthoritiesConstants.ADMIN);
        }
    }

    @Configuration
    @Order(1)
    public class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        OTPService otpService;

        @Autowired
        BookingService bookingService;

        @Bean
        public PasswordEncoder noOpPasswordEncoder() {
            return NoOpPasswordEncoder.getInstance();
        }

        @Bean
        public UserDetailsService otpUserDetailsService() {
            return new OTPUserDetailsServiceImpl(userService, bookingService, otpService);
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
            http.antMatcher("/api/**")
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
                    .loginProcessingUrl("/api/authentication")
                    .successHandler(ajaxAuthenticationSuccessHandler())
                    .failureHandler(ajaxAuthenticationFailureHandler())
                    .permitAll()
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(ajaxLogoutSuccessHandler())
                    .permitAll()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/booking/new").hasAuthority("ROLE_OTP_BOOK")
                    .antMatchers("/api/booking/**").hasAuthority("ROLE_OTP_VIEW")
                    .anyRequest().hasRole("OTP");
        }
    }

}