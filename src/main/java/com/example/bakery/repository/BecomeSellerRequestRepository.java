package com.example.bakery.repository;

import com.example.bakery.model.BecomeSellerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BecomeSellerRequestRepository extends JpaRepository<BecomeSellerRequest, Integer> {
    @Query("SELECT r FROM BecomeSellerRequest r WHERE r.approved = false")
    Iterable<BecomeSellerRequest> findAllUnapproved();

    @Query("SELECT r FROM BecomeSellerRequest r WHERE r.approved = true")
    Iterable<BecomeSellerRequest> findAllApproved();
}
