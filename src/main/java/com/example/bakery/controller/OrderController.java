package com.example.bakery.controller;

import com.example.bakery.exceptionHandler.OrderNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.exceptionHandler.UserNotFoundException;
import com.example.bakery.model.Order;
import com.example.bakery.request.PlaceOrderRequest;
import com.example.bakery.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/checkout")
    public ResponseEntity<Order> checkout(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, OrderNotFoundException, UserNotAuthorisedException {
        return ResponseEntity.ok(orderService.checkout(userDetails));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request,
                            @AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, OrderNotFoundException, UserNotAuthorisedException {
        return ResponseEntity.ok(orderService.placeOrder(request, userDetails));
    }
}
