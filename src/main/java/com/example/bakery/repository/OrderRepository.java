package com.example.bakery.repository;

import com.example.bakery.model.Order;
import com.example.bakery.model.OrderStatus;
import com.example.bakery.model.Seller;
import com.example.bakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUser(User user);
    Optional<Order> findByUserAndStatus(User customer, OrderStatus status);

    Optional<Order> findByUserAndStatusAndSeller(User customer, OrderStatus orderStatus, Seller seller);

    Iterable<Order> findAllByUserAndStatus(User user, OrderStatus orderStatus);

    Iterable<Order> findAllBySellerAndStatus(Seller seller, OrderStatus orderStatus);
}
