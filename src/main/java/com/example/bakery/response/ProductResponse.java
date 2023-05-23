package com.example.bakery.response;

import com.example.bakery.model.Product;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer inStock;
    @NotNull
    private Integer sellerId;
    @NotNull
    private String sellerName;

    public ProductResponse(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.inStock = product.getQuantity();
        this.sellerId = product.getSeller().getId();
        this.sellerName = product.getSeller().getShopName();
    }
}
