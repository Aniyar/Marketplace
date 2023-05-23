package com.example.bakery.response;


import com.example.bakery.model.Seller;
import com.example.bakery.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResponse{
    @NotNull
    private Integer id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String shopName;
    @NotNull @Email
    private String email;

    public SellerResponse(Seller seller){
        this.id = seller.getId();
        this.firstname = seller.getUser().getFirstname();
        this.lastname = seller.getUser().getLastname();
        this.phoneNumber = seller.getUser().getPhoneNumber();
        this.shopName = seller.getShopName();
        this.email = seller.getEmail();
    }
}
