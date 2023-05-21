package com.example.bakery.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AddToCartRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
