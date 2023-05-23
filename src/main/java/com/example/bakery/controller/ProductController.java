package com.example.bakery.controller;


import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.ProductNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.AddToCartRequest;
import com.example.bakery.response.OrderItemResponse;
import com.example.bakery.response.ProductResponse;
import com.example.bakery.service.ProductService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<ProductResponse>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Iterable<ProductResponse>> getAllProductsByCategoryId(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        return ResponseEntity.ok(productService.findAllByCategoryId(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/addToCart")
    public ResponseEntity<OrderItemResponse> addToCart(
            @RequestBody AddToCartRequest request,
            @AuthenticationPrincipal UserDetails userDetails) throws UserNotAuthorisedException, ProductNotFoundException, SellerNotFoundException {
        return ResponseEntity.ok(productService.addToCart(request, userDetails));
    }
}
