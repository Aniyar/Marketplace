package com.example.bakery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Entity
public class BecomeSellerRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @NotNull @Email
    private String email;
    @NotNull
    private String shopName;
    private Boolean approved;
}
