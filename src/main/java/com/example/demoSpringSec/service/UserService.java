package com.example.demoSpringSec.service;

import com.example.demoSpringSec.dto.AuthRequest;
import com.example.demoSpringSec.dto.AuthResponse;
import com.example.demoSpringSec.dto.RegisterRequest;
import com.example.demoSpringSec.models.User;
import com.example.demoSpringSec.repository.UserRepository;
import com.example.demoSpringSec.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public AuthResponse saveUser(RegisterRequest userDetails){
        User user =new User();
        user.setEmail(userDetails.getEmail());
        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setUsername(userDetails.getUsername());
        user.setNationalId(userDetails.getNationalId());
        user.setAddress(userDetails.getAddress());

        userRepository.save(user);
        String token= jwtUtility.generateToken(userDetails.getUsername());
        return new AuthResponse(token,"User Created Successfully");
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        UserDetails user = customUserDetailsService.loadUserByUsername(request.getUsernameOrEmail());
        String token = jwtUtility.generateToken(user.getUsername());

        return new AuthResponse(token, "Login successful");
    }

    public User getUser(String usernameOrEmail){
        User user = new User();
        user = userRepository.findByUsername(usernameOrEmail).orElse(userRepository.findByEmail(usernameOrEmail).orElseThrow());
        return user;
    }

}
