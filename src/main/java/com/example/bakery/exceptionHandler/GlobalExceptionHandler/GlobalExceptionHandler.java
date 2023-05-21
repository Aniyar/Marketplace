package com.example.bakery.exceptionHandler.GlobalExceptionHandler;

import com.example.bakery.exceptionHandler.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ UserNotAuthorisedException.class})
    public final ResponseEntity<ErrorResponse> handleUserNotAuthorised(UserNotAuthorisedException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.UNAUTHORIZED.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ UserNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ CategoryNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleCategoryNotFound(CategoryNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ ProductNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ SellerNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleSellerNotFound(SellerNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ SellerRequestNotFoundException.class})
    public final ResponseEntity<ErrorResponse> handleSellerRequestNotFound(SellerRequestNotFoundException ex){
        ErrorResponse er = new ErrorResponse(ex.getClass().getName(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), null);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }
}
