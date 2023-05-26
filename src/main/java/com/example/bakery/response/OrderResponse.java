package com.example.bakery.response;

import com.example.bakery.model.Order;
import com.example.bakery.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Integer sellerId;
    private String status;
    private List<OrderItemResponse> items;
    private Integer total;
    private String address;
}
