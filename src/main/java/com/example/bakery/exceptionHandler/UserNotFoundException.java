package com.example.bakery.exceptionHandler;

public class UserNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "User Not Found";
    }
}
