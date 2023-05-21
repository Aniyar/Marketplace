package com.example.bakery.exceptionHandler;

public class SellerRequestNotFoundException extends Exception{
    @Override
    public String getMessage(){
        return "Become Seller Request Not Found";
    }
}
