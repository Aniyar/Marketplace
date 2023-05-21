package com.example.bakery.controller;


import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.ProductNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.AddToCartRequest;
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
    public ResponseEntity<Iterable<Product>> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Iterable<Product>> getAllProductsByCategoryId(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        return ResponseEntity.ok(productService.findAllByCategoryId(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productRepository.findById(id).orElseThrow(ProductNotFoundException:: new));
    }

    @PostMapping("/addToCart")
    public ResponseEntity<OrderItem> addToCart(
            @RequestBody AddToCartRequest request,
            @AuthenticationPrincipal UserDetails userDetails) throws UserNotAuthorisedException, ProductNotFoundException {
        return ResponseEntity.ok(productService.addToCart(request, userDetails));
    }
}
