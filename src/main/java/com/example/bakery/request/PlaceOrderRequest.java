package com.example.bakery.request;

import com.example.bakery.model.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceOrderRequest {
    @NotNull
    private String address;
    @NotNull
    private PaymentStatus paymentStatus;
}
