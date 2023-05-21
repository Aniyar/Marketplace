package com.example.bakery.request;

import com.example.bakery.model.ProductCategory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer categoryId;
    @NotNull
    private Integer quantity;
}
