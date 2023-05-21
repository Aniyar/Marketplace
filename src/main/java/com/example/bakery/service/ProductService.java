package com.example.bakery.service;

import com.example.bakery.exceptionHandler.CategoryNotFoundException;
import com.example.bakery.exceptionHandler.ProductNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.AddToCartRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public Iterable<Product> findAllByCategoryId(Integer categoryId) throws CategoryNotFoundException {
        ProductCategory category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException:: new);
        return productRepository.findAllByCategory(category);
    }

    public OrderItem addToCart(AddToCartRequest request, UserDetails userDetails) throws UserNotAuthorisedException, ProductNotFoundException {
        if (userDetails == null) throw new UserNotAuthorisedException();
        User customer = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotAuthorisedException::new);
        Optional<Order> optionalOrder = orderRepository.findByUserAndStatus(customer, OrderStatus.CART);
        Order order = optionalOrder.orElseGet(() -> {
            Order newOrder = Order.builder()
                    .user(customer)
                    .createdAt(new Date(System.currentTimeMillis()))
                    .status(OrderStatus.CART)
                    .paymentStatus(PaymentStatus.AWAITING)
                    .build();

            orderRepository.save(newOrder);
            return newOrder;
        });
        Product product = productRepository.findById(request.getProductId()).orElseThrow(ProductNotFoundException::new);
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .order(order)
                .quantity(request.getQuantity())
                .build();
        return orderItemRepository.save(orderItem);
    }
}
