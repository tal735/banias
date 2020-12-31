package com.app.controller.user;

import com.app.model.user.SessionUser;
import com.app.model.user.User;
import com.app.service.security.SecurityUtils;
import com.app.service.user.UserService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final Cache<Long, String> verificationCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @ResponseBody
    public UserDTO getUserDetails() {
        SessionUser user = SecurityUtils.getLoggedInUser();
        return new UserDTO(user);
    }

    @PostMapping("/user/register")
    public ResponseEntity register(@RequestBody UserSignupRequest userSignupRequest) {
        try {
            String email = userSignupRequest.getEmail();
            String password = userSignupRequest.getPassword();
            if (StringUtils.isBlank(password) || password.length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password invalid.");
            }
            if (StringUtils.isBlank(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email invalid.");
            }
            email = email.toLowerCase().trim();
            if (userService.getByEmail(email) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use.");
            }
            userService.addNewUser(userSignupRequest.getFirstName(), userSignupRequest.getLastName(), email, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating a new user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/api/user/update-name")
    public ResponseEntity updateName(@RequestParam String firstName,
                                     @RequestParam String lastName) {
        User user = userService.getById(SecurityUtils.getLoggedInUser().getUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.saveOrUpdate(user);
        updateContext(user);

        SessionUser loggedInUser = SecurityUtils.getLoggedInUser();
        return ResponseEntity.ok(new UserDTO(loggedInUser));
    }

    @PostMapping("/api/user/update-email")
    public ResponseEntity updateEmail(@RequestParam String email) {
        email = email.toLowerCase().trim();
        if (userService.getByEmail(email) != null) {
            return ResponseEntity.badRequest().body("Email is already in use.");
        }
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        User user = userService.getById(userId);
        user.setEmail(email);
        userService.saveOrUpdate(user);
        updateContext(user);

        SessionUser loggedInUser = SecurityUtils.getLoggedInUser();
        return ResponseEntity.ok(new UserDTO(loggedInUser));
    }

    @PostMapping("/api/user/update-phone")
    public ResponseEntity updatePhone(@RequestParam String countryCode, @RequestParam String phone) {
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        User user = userService.getById(userId);
        user.setCountryCode(countryCode);
        user.setPhone(phone);
        user.setVerified(false);
        userService.saveOrUpdate(user);
        updateContext(user);

        SessionUser loggedInUser = SecurityUtils.getLoggedInUser();
        return ResponseEntity.ok(new UserDTO(loggedInUser));
    }

    @PostMapping("/api/user/token")
    public ResponseEntity sendPhoneToken() {
        Long userId = SecurityUtils.getLoggedInUser().getUserId();
        String token = RandomStringUtils.randomNumeric(6);
        verificationCache.invalidate(userId);
        verificationCache.put(userId, token);
        LOGGER.debug("user " + userId + " , token " + token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/user/verify")
    public ResponseEntity verifyPhoneToken(@RequestParam String token) {
        Long userId = SecurityUtils.getLoggedInUser().getUserId();

        String savedToken = verificationCache.getIfPresent(userId);
        if (savedToken == null || !StringUtils.equals(savedToken, token)) {
            return ResponseEntity.badRequest().body("Code is not valid. Try again.");
        }

        User user = userService.getById(userId);
        user.setVerified(true);
        userService.saveOrUpdate(user);
        updateContext(user);

        return ResponseEntity.ok(new UserDTO(SecurityUtils.getLoggedInUser()));
    }

    private void updateContext(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new SessionUser(user), authentication.getCredentials(), authentication.getAuthorities());
        token.setDetails(authentication.getDetails());

        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
