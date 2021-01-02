package com.app.controller.user;

import com.app.service.security.SecurityUtils;
import com.app.service.user.SessionUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user")
    @ResponseBody
    public UserDTO getUserDetails() {
        SessionUserDetails user = SecurityUtils.getLoggedInUser();
        return new UserDTO(user);
    }

}
