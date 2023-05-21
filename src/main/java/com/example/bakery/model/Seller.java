package com.example.bakery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table
public class Seller{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String shopName;

    @NotNull @Email
    private String email;
}
