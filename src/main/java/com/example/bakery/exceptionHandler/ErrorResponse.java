package com.example.bakery.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private String name;
    private String code;
    private String message;
    private String description;
}