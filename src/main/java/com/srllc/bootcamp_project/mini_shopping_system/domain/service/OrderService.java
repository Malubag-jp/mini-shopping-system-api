package com.srllc.bootcamp_project.mini_shopping_system.domain.service;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Order;

import java.util.List;

public interface OrderService {
    OrderResponseDto createOrder(OrderDto orderDto, String username);
    List<OrderResponseDto> getMyOrders(String username);
    List<OrderResponseDto> getAllOrders();
    OrderResponseDto updateOrderStatus(Long orderId, String statusName);
}