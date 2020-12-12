package com.app.controller.home;

import com.app.model.user.SessionUser;
import com.app.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @GetMapping("/home")
    public String index() {
        logger.debug("Hello from Logback");
        return "HI";
    }

    @GetMapping("/api/home")
    public String apiHome() {
        SessionUser user = SecurityUtils.getLoggedInUser();
        logger.debug("Hello from apiHome");
        return "apiHome";
    }
}
