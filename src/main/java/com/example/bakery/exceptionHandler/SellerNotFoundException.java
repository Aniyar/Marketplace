package com.example.bakery.exceptionHandler;

public class SellerNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Seller Not Found";
    }
}
