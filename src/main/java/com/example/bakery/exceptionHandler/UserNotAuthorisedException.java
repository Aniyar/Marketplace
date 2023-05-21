package com.example.bakery.exceptionHandler;

public class UserNotAuthorisedException extends Exception {
    @Override
    public String getMessage() {
        return "User not authorized. Redirect to login";
    }
}
