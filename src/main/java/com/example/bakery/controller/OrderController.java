package com.example.bakery.controller;

import com.example.bakery.exceptionHandler.OrderNotFoundException;
import com.example.bakery.exceptionHandler.OutOfStockException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.exceptionHandler.UserNotFoundException;
import com.example.bakery.model.Order;
import com.example.bakery.request.PlaceOrderRequest;
import com.example.bakery.response.OrderResponse;
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
    public ResponseEntity<Iterable<OrderResponse>> checkout(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, OrderNotFoundException, UserNotAuthorisedException {
        return ResponseEntity.ok(orderService.checkout(userDetails));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderRequest request,
                            @AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, UserNotAuthorisedException, OutOfStockException {
        orderService.placeOrder(request, userDetails);
        return ResponseEntity.ok().build();
    }
}
