package com.app.controller.user;

import com.app.model.user.SessionUser;
import com.app.service.security.SecurityUtils;
import com.app.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @ResponseBody
    public UserDTO getUserDetails() {
        UserDTO userDTO = new UserDTO();

        SessionUser user = SecurityUtils.getLoggedInUser();
        if (user != null) {
            List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).map(role -> role.replace("ROLE_", "")).collect(Collectors.toList());
            roles.add("ADMIN");
            userDTO.setEmail(user.getEmail());
            userDTO.setRoles(roles);
        }

        return userDTO;
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
            userService.addNewUser(email, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error while creating a new user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
