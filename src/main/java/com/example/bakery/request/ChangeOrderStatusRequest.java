package com.example.bakery.request;


import com.example.bakery.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeOrderStatusRequest {
    private Long orderId;
    private String orderStatus;
}
