package com.example.bakery.service;

import com.example.bakery.exceptionHandler.OrderNotFoundException;
import com.example.bakery.exceptionHandler.UserNotAuthorisedException;
import com.example.bakery.exceptionHandler.UserNotFoundException;
import com.example.bakery.model.*;
import com.example.bakery.repository.OrderRepository;
import com.example.bakery.repository.ProductRepository;
import com.example.bakery.repository.UserRepository;
import com.example.bakery.request.PlaceOrderRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Order placeOrder(PlaceOrderRequest request, UserDetails userDetails) throws UserNotFoundException, UserNotAuthorisedException, OrderNotFoundException {
        if (userDetails == null) throw new UserNotAuthorisedException();
        User user = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Order order = orderRepository.findByUserAndStatus(user, OrderStatus.CART).orElseThrow(OrderNotFoundException:: new);
        order.getOrderItems().forEach(orderItem -> {
            Product product = orderItem.getProduct();
            if (product.getQuantity() <= orderItem.getQuantity()) {
                product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                productRepository.save(product);
            }
        });
        order.setPaymentStatus(request.getPaymentStatus());
        order.setStatus(OrderStatus.READY);
        return orderRepository.save(order);
    }

    public Order checkout(UserDetails userDetails) throws UserNotAuthorisedException, UserNotFoundException, OrderNotFoundException {
        if (userDetails == null) throw new UserNotAuthorisedException();
        User user = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Order order = orderRepository.findByUserAndStatus(user, OrderStatus.CART).orElseThrow(OrderNotFoundException::new);
        order.setTotal(calculateTotal(order));
        return order;
    }


    public Integer calculateTotal(Order order){
        return order.getOrderItems().stream()
                .mapToInt(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }
}
