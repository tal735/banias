package com.app.controller.user;

import com.app.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register")
    public ResponseEntity register(@RequestParam String email, @RequestParam String password) {
        try {
            email = email.toLowerCase().trim();
            if (StringUtils.isBlank(email) || userService.getByEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use.");
            }
            userService.addNewUser(email, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating a new user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
