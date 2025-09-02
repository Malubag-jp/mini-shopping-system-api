package com.srllc.bootcamp_project.mini_shopping_system.domain.service.Impl;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.*;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderItemDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderItemResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.*;
import com.srllc.bootcamp_project.mini_shopping_system.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final StatusDao statusDao;
    private final OrderItemDao orderItemDao;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderDto orderDto, String username) {
        log.info("Creating order for user: {}", username);

        // Find user
        User user = userDao.findByUserNameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        // Find pending status
        Status pendingStatus = statusDao.findByStatusName("PENDING");
        if (pendingStatus == null) {
            throw new IllegalArgumentException("PENDING status not found in database");
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(pendingStatus);
        order.setTotalAmount(0.0);

        Order savedOrder = orderDao.save(order);

        // Process order items
        double totalAmount = 0.0;
        for (OrderItemDto itemDto : orderDto.getItems()) {
            Product product = productDao.findById(itemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemDto.getProductId()));

            // Check stock
            if (product.getStock() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemDao.save(orderItem);

            // Update product stock
            product.setStock(product.getStock() - itemDto.getQuantity());
            productDao.save(product);

            // Calculate total
            totalAmount += product.getPrice() * itemDto.getQuantity();
        }

        // Update order total
        savedOrder.setTotalAmount(totalAmount);
        Order finalOrder = orderDao.save(savedOrder);

        log.info("Order created successfully with ID: {}", finalOrder.getId());
        return convertToOrderResponseDto(finalOrder);
    }

    @Override
    public List<OrderResponseDto> getMyOrders(String username) {
        log.info("Fetching orders for user: {}", username);

        User user = userDao.findByUserNameOrEmail(username, username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        List<Order> orders = orderDao.findByUserId(user.getId());
        return orders.stream()
                .map(this::convertToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Order> orders = orderDao.findAll();
        return orders.stream()
                .map(this::convertToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId, String statusName) {
        log.info("Updating order {} status to {}", orderId, statusName);

        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));

        Status status = statusDao.findByStatusName(statusName);
        if (status == null) {
            throw new IllegalArgumentException("Status not found: " + statusName);
        }

        order.setStatus(status);
        Order updatedOrder = orderDao.save(order);

        log.info("Order status updated successfully");
        return convertToOrderResponseDto(updatedOrder);
    }

    private OrderResponseDto convertToOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        dto.setCustomerEmail(order.getUser().getEmail());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().getStatusName());

        List<OrderItemResponseDto> itemDtos = order.getItems().stream()
                .map(this::convertToOrderItemResponseDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        return dto;
    }

    private OrderItemResponseDto convertToOrderItemResponseDto(OrderItem item) {
        OrderItemResponseDto dto = new OrderItemResponseDto();
        dto.setId(item.getId());
        dto.setProductName(item.getProduct().getName());
        dto.setProductPrice(item.getProduct().getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setTotalPrice(item.getPrice() * item.getQuantity());
        return dto;
    }
}