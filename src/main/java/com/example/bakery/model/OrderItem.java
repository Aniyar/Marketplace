package com.example.bakery.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @ManyToOne
    @JoinColumn(name="_order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name="quantity", nullable = false)
    private int quantity;
}
