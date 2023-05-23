package com.example.bakery.service;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.ProductNotFoundException;
import com.example.bakery.exceptionHandler.SellerNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.AddToCartRequest;
import com.example.bakery.response.OrderItemResponse;
import com.example.bakery.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final SellerRepository sellerRepository;

    public Iterable<ProductResponse> findAllByCategoryId(Integer categoryId) throws CategoryNotFoundException {
        ProductCategory category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException:: new);
        Iterable<Product> products = productRepository.findAllByCategory(category);
        return  StreamSupport.stream(products.spliterator(), true)
                .map(product -> new ProductResponse(product))
                .collect(Collectors.toList());
    }

    public OrderItemResponse addToCart(AddToCartRequest request, UserDetails userDetails) throws UserNotAuthorisedException, ProductNotFoundException, SellerNotFoundException {
        if (userDetails == null) throw new UserNotAuthorisedException();
        User customer = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotAuthorisedException::new);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(ProductNotFoundException::new);
        Seller seller = sellerRepository.findById(product.getSeller().getId()).orElseThrow(SellerNotFoundException::new);
        Optional<Order> optionalOrder = orderRepository.findByUserAndStatusAndSeller(customer, OrderStatus.CART, seller);
        Order order = optionalOrder.orElseGet(() -> {
            Order newOrder = Order.builder()
                    .user(customer)
                    .createdAt(new Date(System.currentTimeMillis()))
                    .status(OrderStatus.CART)
                    .paymentStatus(PaymentStatus.AWAITING)
                    .seller(seller)
                    .build();
            orderRepository.save(newOrder);
            return newOrder;
        });
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .order(order)
                .quantity(request.getQuantity())
                .build();
        orderItemRepository.save(orderItem);
        return new OrderItemResponse(orderItem);
    }

    public Iterable<ProductResponse> getAllProducts() {
        return  StreamSupport.stream(productRepository.findAll().spliterator(), true)
                .map(product -> new ProductResponse(product))
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException:: new);
        return new ProductResponse(product);
    }
}
