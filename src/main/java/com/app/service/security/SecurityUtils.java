package com.app.service.security;

import com.app.service.user.SessionUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public final class SecurityUtils {

    public static SessionUserDetails getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof SessionUserDetails) {
            return  (SessionUserDetails) authentication.getPrincipal();
        }
        return null;
    }

}
