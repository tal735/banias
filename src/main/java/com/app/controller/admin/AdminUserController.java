package com.app.controller.admin;

import com.app.model.user.User;
import com.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    private final UserService userService;

    @Autowired
    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/exists")
    @ResponseBody
    public boolean exists(@RequestParam String email) {
        String emailToCheck = email.toLowerCase().trim();
        User user = userService.getByEmail(emailToCheck);
        return user != null;
    }

    @PostMapping("/create")
    @ResponseBody
    public boolean create(@RequestParam String email) {
        String emailToUse = email.toLowerCase().trim();
        userService.getOrCreateUser(emailToUse);
        return true;
    }

}
