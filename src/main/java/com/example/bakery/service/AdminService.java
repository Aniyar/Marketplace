package com.example.bakery.service;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.SellerRequestNotFoundException;
import com.example.bakery.exceptionHandler.UserNotFoundException;
import com.example.bakery.model.*;
import com.example.bakery.repository.BecomeSellerRequestRepository;
import com.example.bakery.repository.ProductCategoryRepository;
import com.example.bakery.repository.SellerRepository;
import com.example.bakery.repository.UserRepository;
import com.example.bakery.request.CategoryRequest;
import com.example.bakery.request.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final ProductCategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final BecomeSellerRequestRepository BSRrepository;

    public void deleteSeller(Integer id) throws SellerNotFoundException {
        Seller seller = sellerRepository.findById(id).orElseThrow(SellerNotFoundException:: new);
        User sellerUser = seller.getUser();
        sellerUser.setRole(Role.USER);
        userRepository.save(sellerUser);
        sellerRepository.deleteById(seller.getId());
        return;
    }

    public Seller approveSellerRequest(Integer id) throws SellerRequestNotFoundException {
        // Set request as approved
        BecomeSellerRequest sellerRequest = BSRrepository.findById(id).orElseThrow(SellerRequestNotFoundException:: new);
        sellerRequest.setApproved(true);
        BSRrepository.save(sellerRequest);

        // Register user for seller
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname(sellerRequest.getFirstName())
                .lastname(sellerRequest.getLastName())
                .phoneNumber(sellerRequest.getPhoneNumber())
                .password("123")
                .build();
        authService.register(registerRequest, Role.SELLER);

        // Register new seller
        User sellerUser = userRepository.findByPhoneNumber(sellerRequest.getPhoneNumber()).orElseThrow(UserNotFoundException:: new);

        return sellerRepository.save(Seller.builder()
                .shopName(sellerRequest.getShopName())
                .user(sellerUser)
                .email(sellerRequest.getEmail())
                .build());
    }

    public ProductCategory deleteCategory(Integer id) throws CategoryNotFoundException {
        ProductCategory category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.deleteById(category.getId());
    }

    public ProductCategory editCategory(CategoryRequest request) {
        ProductCategory category = categoryRepository.findById(request.getId()).orElseThrow(CategoryNotFoundException::new);
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

    public ProductCategory addCategory(CategoryRequest request) {
        return categoryRepository.save(ProductCategory.builder()
                                                        .name(request.getName())
                                                        .build());
    }
}
