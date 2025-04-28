package com.example.demoSpringSec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
