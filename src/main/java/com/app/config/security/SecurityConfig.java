package com.app.config.security;

import com.app.config.security.csrf.CsrfHeaderFilter;
import com.app.config.security.dao.DAOAuthenticationProvider;
import com.app.config.security.dao.DaoAuthenticationFilter;
import com.app.config.security.otp.OTPAuthenticationFilter;
import com.app.config.security.otp.OTPAuthenticationProvider;
import com.app.service.booking.BookingService;
import com.app.service.otp.OTPService;
import com.app.config.security.dao.UserDetailsServiceImpl;
import com.app.service.user.UserService;
import com.app.config.security.otp.OTPUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired UserService userService;
    @Autowired OTPService otpService;
    @Autowired BookingService bookingService;
    @Autowired AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired AccessDeniedHandler accessDeniedHandler;
    @Autowired LogoutSuccessHandler logoutSuccessHandler;
    @Autowired @Qualifier("AjaxAuthenticationSuccessHandler") AuthenticationSuccessHandler successHandler;
    @Autowired @Qualifier("OTPAuthenticationSuccessHandler")  AuthenticationSuccessHandler otpSuccessHandler;
    @Autowired AuthenticationFailureHandler failureHandler;

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
        DAOAuthenticationProvider provider = new DAOAuthenticationProvider();
        provider.setUserDetailsService(UserDetailsService());
        provider.setPasswordEncoder(BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService otpUserDetailsService() {
        return new OTPUserDetailsServiceImpl(userService, bookingService, otpService);
    }

    @Bean
    public AuthenticationProvider otpAuthenticationProvider() {
        OTPAuthenticationProvider provider = new OTPAuthenticationProvider();
        provider.setUserDetailsService(otpUserDetailsService());
        provider.setPasswordEncoder(BCryptPasswordEncoder());
        return provider;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // configure filters
        http.addFilterBefore( new DaoAuthenticationFilter("/authentication", successHandler, failureHandler, authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class );
        http.addFilterBefore( new OTPAuthenticationFilter("/api/authentication", otpSuccessHandler, failureHandler, authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class );
        http.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);

        // configure authentication providers
        http.authenticationProvider( authenticationProvider() );
        http.authenticationProvider( otpAuthenticationProvider() );

        http.authorizeRequests()
                .antMatchers("/user").permitAll()
                .antMatchers("/otp/**").anonymous()
                .antMatchers("/api/**").hasAuthority(AuthoritiesConstants.USER)
                .antMatchers("/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .and()
                .cors().configurationSource(SecurityConfig::getCorsConfiguration)
                .and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler);
    }

    private static CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("https://www.vaniascamping.com");
        config.addAllowedOrigin("https://vaniascamping.com");
        config.setAllowCredentials(true);
        return config;
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

}