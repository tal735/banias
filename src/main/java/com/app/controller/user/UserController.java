package com.app.controller.user;

import com.app.model.user.User;
import com.app.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    @ResponseBody
    public User register(@RequestParam String email, @RequestParam String password) {
        try {
            return userService.addNewUser(email, password);
        } catch (Exception e) {
            logger.error("Error while creating a new user", e);
            return null;
        }
    }
}
