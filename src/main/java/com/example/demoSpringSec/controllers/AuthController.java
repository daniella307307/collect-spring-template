package com.example.demoSpringSec.controllers;

import com.example.demoSpringSec.dto.AuthRequest;
import com.example.demoSpringSec.dto.AuthResponse;
import com.example.demoSpringSec.dto.RegisterRequest;
import com.example.demoSpringSec.models.User;
import com.example.demoSpringSec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(userService.saveUser(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        return ResponseEntity.ok(userService.login(request));
    }
    @GetMapping ("/users/get-all-users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

}
