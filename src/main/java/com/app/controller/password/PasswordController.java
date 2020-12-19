package com.app.controller.password;

import com.app.model.password.PasswordReset;
import com.app.model.user.User;
import com.app.service.password.PasswordService;
import com.app.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PasswordController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordController.class);

    private final PasswordService passwordService;
    private final UserService userService;

    @Autowired
    public PasswordController(PasswordService passwordService, UserService userService) {
        this.passwordService = passwordService;
        this.userService = userService;
    }

    @PostMapping("/forgot-password")
    @ResponseBody
    public ResponseEntity forgotPassword(@RequestBody String email) {
        try {
            LOGGER.debug("forgotPassword: Sending reset token for email  " + email);
            User user = userService.getByEmail(email.trim());
            if (user == null) {
                return ResponseEntity.badRequest().body("Cannot reset email for this user.");
            }
            String token = passwordService.createResetToken(user.getId());
            LOGGER.debug("created token=" + token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating reset token for user: " + email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/reset-password")
    @ResponseBody
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest request) {
        String token = request.getToken();
        String password = request.getPassword();
        try {
            LOGGER.debug("resetPassword: Resetting password for token " + token);
            PasswordReset passwordReset = passwordService.getByToken(token);
            if (passwordReset == null || passwordReset.getUsed() ||
                    passwordReset.getDateCreated().before(DateTime.now().minusDays(7).toDate())) {
                return ResponseEntity.badRequest().body("Token is invalid");
            }
            if (StringUtils.length(password) < 6) {
                return ResponseEntity.badRequest().body("Password too weak.");
            }
            passwordService.resetPassword(token, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while resetting password for token: " + token, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
