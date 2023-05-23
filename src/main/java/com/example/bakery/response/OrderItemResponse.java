package com.example.bakery.response;

import com.example.bakery.model.OrderItem;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    @NotNull
    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private String productName;
    @NotNull
    private Integer price;
    @NotNull
    private Integer quantity;
    @NotNull
    private Long orderId;

    public OrderItemResponse(OrderItem item){
        this.id = item.getId();
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.price = item.getProduct().getPrice();
        this.quantity = item.getQuantity();
        this.orderId = item.getOrder().getId();
    }


}
