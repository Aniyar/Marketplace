package com.example.bakery.exceptionHandler;

public class ProductNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Product Not Found";
    }
}
