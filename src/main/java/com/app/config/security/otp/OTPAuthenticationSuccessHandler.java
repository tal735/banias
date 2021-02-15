package com.app.config.security.otp;

import com.app.service.otp.OTPService;
import com.app.service.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("OTPAuthenticationSuccessHandler")
public class OTPAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final OTPService otpService;

    @Autowired
    public OTPAuthenticationSuccessHandler(OTPService otpService) {
        this.otpService = otpService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        String email = SecurityUtils.getLoggedInUser().getUsername();
        otpService.invalidate(email);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
