package com.example.bakery.repository;

import com.example.bakery.model.Seller;
import com.example.bakery.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByUser(User user);
}
