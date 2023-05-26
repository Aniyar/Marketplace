package com.example.bakery.service;

import com.example.bakery.exceptionHandler.*;
import com.example.bakery.model.*;
import com.example.bakery.repository.*;
import com.example.bakery.request.ChangeOrderStatusRequest;
import com.example.bakery.request.PlaceOrderRequest;
import com.example.bakery.response.OrderItemResponse;
import com.example.bakery.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final SellerRepository sellerRepository;

    public void placeOrder(PlaceOrderRequest request, UserDetails userDetails) throws UserNotFoundException, OutOfStockException, UserNotAuthorisedException {
        if (userDetails == null) throw new UserNotAuthorisedException();
        User user = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Iterable<Order> orders = orderRepository.findAllByUserAndStatus(user, OrderStatus.CART);
        orders.forEach(
                order -> {
                    Iterable<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
                    orderItems.forEach(orderItem -> {
                        Product product = orderItem.getProduct();
                        if (product.getQuantity() >= orderItem.getQuantity()) {
                            product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                            productRepository.save(product);
                        } else{
                            throw OutOfStockException.builder()
                                    .productId(product.getId())
                                    .productName(product.getName())
                                    .inStock(product.getQuantity())
                                    .build();
                        }
                    });
                    order.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus()));
                    order.setShippingAddress(request.getAddress());
                    order.setStatus(OrderStatus.READY);
                    orderRepository.save(order);
                }
        );
    }

    public Iterable<OrderResponse> checkout(UserDetails userDetails) throws UserNotAuthorisedException, UserNotFoundException, OrderNotFoundException {
        if (userDetails == null) throw new UserNotAuthorisedException();
        User user = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        Iterable<Order> orders = orderRepository.findAllByUserAndStatus(user, OrderStatus.CART);
        List<OrderResponse> responseList = new ArrayList<OrderResponse>();
        orders.forEach(
                order -> {
                    Iterable<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
                    Integer total = calculateTotal(orderItems);
                    order.setTotal(total);
                    orderRepository.save(order);
                    responseList.add(convertToOrderResponse(order));
                }
        );
        return responseList;
    }

    public Iterable<OrderResponse> sellerGetOrders(UserDetails userDetails, OrderStatus status) throws UserNotAuthorisedException, SellerNotFoundException {
        User sellerUser = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotAuthorisedException::new);
        Seller seller = sellerRepository.findByUser(sellerUser).orElseThrow(SellerNotFoundException::new);
        Iterable<Order> orders = orderRepository.findAllBySellerAndStatus(seller, status);
        return StreamSupport.stream(orders.spliterator(), true)
                .map(order -> convertToOrderResponse(order))
                .collect(Collectors.toList());

    }


    public Integer calculateTotal(Iterable<OrderItem> orderItems){
        return StreamSupport.stream(orderItems.spliterator(), false)
                .mapToInt(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }

    public OrderResponse convertToOrderResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .sellerId(order.getSeller().getId())
                .status(order.getStatus().name())
                .items(StreamSupport.stream(orderItemRepository.findAllByOrderId(order.getId()).spliterator(), true)
                        .map(orderItem -> new OrderItemResponse(orderItem))
                        .collect(Collectors.toList()))
                .total(order.getTotal())
                .address(order.getShippingAddress())
                .build();
    }

    public OrderResponse sellerChangeOrderStatus(UserDetails userDetails, Long orderId, OrderStatus status) throws UserNotAuthorisedException, SellerNotFoundException, OrderNotFoundException {
        User sellerUser = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotAuthorisedException::new);
        Seller seller = sellerRepository.findByUser(sellerUser).orElseThrow(SellerNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        order.setStatus(status);
        orderRepository.save(order);
        return  convertToOrderResponse(order);
    }

    public OrderResponse getOrderById(UserDetails userDetails, Long id) throws OrderNotFoundException, SellerNotFoundException, UserNotAuthorisedException {
        User sellerUser = userRepository.findByPhoneNumber(userDetails.getUsername()).orElseThrow(UserNotAuthorisedException::new);
        Seller seller = sellerRepository.findByUser(sellerUser).orElseThrow(SellerNotFoundException::new);
        Order order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
        return convertToOrderResponse(order);
    }
}
