package com.example.bakery.model;

import com.example.bakery.repository.ProductCategoryRepository;
import com.example.bakery.repository.SellerRepository;
import com.example.bakery.repository.UserRepository;
import com.example.bakery.request.RegisterRequest;
import com.example.bakery.service.AuthenticationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadDatabase {
    private final UserRepository userRepository;
    private final AuthenticationService authService;
    private final ProductCategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    @PostConstruct
    public void initDatabase(){
        if (userRepository.count() == 0){
            authService.register(
                    RegisterRequest.builder()
                            .firstname("admin")
                            .phoneNumber("+77777777777")
                            .password("admin")
                            .build(),
                    Role.ADMIN);
        }

        if (categoryRepository.count() == 0){
            categoryRepository.saveAll(List.of(
                    ProductCategory.builder().name("meat").build(),
                    ProductCategory.builder().name("bread").build(),
                    ProductCategory.builder().name("sweet bread").build(),
                    ProductCategory.builder().name("kymyz").build()
            ));
        }


    }



}
