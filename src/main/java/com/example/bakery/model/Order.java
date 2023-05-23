package com.example.bakery.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="_order")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    @NotNull
    private Date createdAt;
    private Date pickedUpAt;
    private Date shippedAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String shippingAddress;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Integer total;
    @NotNull
    @ManyToOne
    @JoinColumn(name="seller_id")
    private Seller seller;
}
