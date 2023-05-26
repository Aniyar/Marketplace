package com.example.bakery.controller;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.OrderNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.*;
import com.example.bakery.request.AddProductRequest;
import com.example.bakery.response.OrderResponse;
import com.example.bakery.service.OrderService;
import com.example.bakery.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {
    private final SellerService sellerService;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> getSellerProducts(){
        return ResponseEntity.ok(new ArrayList<Product>());
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(
            @RequestBody AddProductRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws UserNotAuthorisedException, CategoryNotFoundException, SellerNotFoundException {
        return ResponseEntity.ok(sellerService.addProduct(request, userDetails));
    }

    @GetMapping("/orders/new")
    public ResponseEntity<Iterable<OrderResponse>> newOrders(
            @AuthenticationPrincipal UserDetails userDetails) throws UserNotAuthorisedException, SellerNotFoundException {
        return ResponseEntity.ok(orderService.sellerGetOrders(userDetails, OrderStatus.READY));
    }

    @GetMapping("/orders/delivery")
    public ResponseEntity<Iterable<OrderResponse>> deliveryOrders(
            @AuthenticationPrincipal UserDetails userDetails) throws UserNotAuthorisedException, SellerNotFoundException {
        return ResponseEntity.ok(orderService.sellerGetOrders(userDetails, OrderStatus.DELIVERY));
    }

    @GetMapping("/orders/completed")
    public ResponseEntity<Iterable<OrderResponse>> completedOrders(
            @AuthenticationPrincipal UserDetails userDetails) throws UserNotAuthorisedException, SellerNotFoundException {
        return ResponseEntity.ok(orderService.sellerGetOrders(userDetails, OrderStatus.SHIPPED));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws OrderNotFoundException, UserNotAuthorisedException, SellerNotFoundException {
        return ResponseEntity.ok(orderService.getOrderById(userDetails, id));
    }

    @PutMapping("/orders/{id}/startDelivery")
    public ResponseEntity<OrderResponse> startDelivery(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) throws OrderNotFoundException, UserNotAuthorisedException, SellerNotFoundException {
        return ResponseEntity.ok(orderService.sellerChangeOrderStatus(userDetails, id, OrderStatus.DELIVERY));
    }

    @PutMapping("/orders/{id}/complete")
    public ResponseEntity<OrderResponse> completeOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) throws OrderNotFoundException, UserNotAuthorisedException, SellerNotFoundException {
        return ResponseEntity.ok(orderService.sellerChangeOrderStatus(userDetails, id, OrderStatus.SHIPPED));
    }
}
