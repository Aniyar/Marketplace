package com.example.bakery.service;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.Product;
import com.example.bakery.model.ProductCategory;
import com.example.bakery.model.Seller;
import com.example.bakery.model.User;
import com.example.bakery.repository.ProductCategoryRepository;
import com.example.bakery.repository.ProductRepository;
import com.example.bakery.repository.SellerRepository;
import com.example.bakery.repository.UserRepository;
import com.example.bakery.request.AddProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SellerService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    public Product addProduct(AddProductRequest request, UserDetails userDetails) throws UserNotAuthorisedException, CategoryNotFoundException, SellerNotFoundException {
        User sellerUser = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotAuthorisedException::new);
        ProductCategory category = categoryRepository.findById(request.getCategoryId()).orElseThrow(CategoryNotFoundException::new);
        Seller seller = sellerRepository.findByUser(sellerUser).orElseThrow(SellerNotFoundException::new);
        return productRepository.save(Product.builder()
                                                .name(request.getName())
                                                .price(request.getPrice())
                                                .category(category)
                                                .seller(seller)
                                                .quantity(request.getQuantity())
                                                .build());
    }
}
