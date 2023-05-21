package com.example.bakery.controller;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.AddProductRequest;
import com.example.bakery.model.BecomeSellerRequest;
import com.example.bakery.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller")
@PreAuthorize("hasRole('SELLER')")
public class SellerController {
    private final SellerService sellerService;

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
}
