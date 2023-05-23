package com.example.bakery.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class OutOfStockException extends RuntimeException {
    private Long productId;
    private String productName;
    private Integer inStock;

    @Override
    public String getMessage(){
        return "Requested product with id " + productId + " and name " + productName
                + " is out of stock. Number of products currently in stock: " + inStock;
    }

}
