package com.app.controller.admin;

import com.app.controller.user.UserDTO;
import com.app.model.user.User;
import com.app.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/find")
    @ResponseBody
    public UserDTO findUser(@RequestParam String email) {
        User user = userService.getByEmail(email);
        return user == null ? null : new UserDTO(user);
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public UserDTO update(@RequestParam String email, @RequestParam String value, @RequestParam String field) {
        User user = userService.getByEmail(email);

        if (user != null) {
            switch (field) {
                case "firstName":
                    user.setFirstName(value);
                    break;
                case "lastName":
                    user.setLastName(value);
                    break;
                case "email":
                    String newEmail = value.toLowerCase().trim();
                    if (userService.getByEmail(newEmail) == null) {
                        user.setEmail(newEmail);
                    }
                    break;
                case "countryCode":
                    user.setCountryCode(value);
                    break;
                case "phone":
                    user.setPhone(value);
                    break;
                case "verified":
                    user.setVerified(Boolean.parseBoolean(value));
                    break;
                case "type":
                    user.setType(value);
                    break;
                case "adminNotes":
                    user.setAdminNotes(value);
                    break;
            }
            userService.saveOrUpdate(user);
        }

        return user == null ? null : new UserDTO(user);
    }
}
