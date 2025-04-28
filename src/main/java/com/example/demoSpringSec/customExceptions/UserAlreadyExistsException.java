package com.example.demoSpringSec.customExceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
       super(message);
    }
}
