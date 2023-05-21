package com.example.bakery.exceptionHandler;

public class CategoryNotFoundException extends Exception {
    @Override
    public String getMessage(){
        return "Category Not Found";
    }
}
