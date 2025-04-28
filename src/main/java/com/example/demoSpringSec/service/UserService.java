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

import java.util.List;

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
        return userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail).orElseThrow(()->new RuntimeException("User not found"));
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public void deleteUser(Long id){
      if(!userRepository.existsById(id)){
          throw new RuntimeException("User not found");
      }
      userRepository.deleteById(id);

    }

    public User updateUser(Long id, RegisterRequest userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()
                && !user.getUsername().equals(userDetails.getUsername())) {

            if (userRepository.existsByUsername(userDetails.getUsername())) {
                throw new RuntimeException("Username already taken");
            }
            user.setUsername(userDetails.getUsername());
        }

        if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()
                && !user.getEmail().equals(userDetails.getEmail())) {

            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new RuntimeException("Email already taken");
            }
            user.setEmail(userDetails.getEmail());
        }

        if (userDetails.getFirstname() != null && !userDetails.getFirstname().isEmpty()) {
            user.setFirstname(userDetails.getFirstname());
        }

        if (userDetails.getLastname() != null && !userDetails.getLastname().isEmpty()) {
            user.setLastname(userDetails.getLastname());
        }

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        if (userDetails.getNationalId() != null && !userDetails.getNationalId().isEmpty()) {
            user.setNationalId(userDetails.getNationalId());
        }

        if (userDetails.getAddress() != null && !userDetails.getAddress().isEmpty()) {
            user.setAddress(userDetails.getAddress());
        }

        return userRepository.save(user);
    }

}
