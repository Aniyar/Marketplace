package com.example.bakery.repository;

import com.example.bakery.model.Product;
import com.example.bakery.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findAllByCategory(ProductCategory category);
}
