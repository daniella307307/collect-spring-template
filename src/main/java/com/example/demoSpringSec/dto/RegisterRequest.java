package com.example.demoSpringSec.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String nationalId;
    private String username;
    private String email;
    private String password;
    private String address;
}
