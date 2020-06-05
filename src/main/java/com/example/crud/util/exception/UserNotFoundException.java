package com.example.crud.util.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id) {
        super("User id not found : " + id);
    }
}
