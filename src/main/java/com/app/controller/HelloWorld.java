package com.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/hi")
    public String index() {
        return "HI";
    }

}
