package com.example.bakery.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Builder
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @NotNull
    private Integer quantity;
}
