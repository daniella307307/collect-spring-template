package com.example.demoSpringSec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello"; // This will resolve to src/main/resources/templates/hello.html
    }
}
