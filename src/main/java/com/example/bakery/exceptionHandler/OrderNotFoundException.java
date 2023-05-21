package com.example.bakery.exceptionHandler;

public class OrderNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Order not found";
    }
}
