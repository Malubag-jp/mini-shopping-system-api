package com.srllc.bootcamp_project.mini_shopping_system.domain.controller;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Customer order operations")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create new order", description = "Customer can create a new order")
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderDto orderDto, Authentication authentication) {
        OrderResponseDto order = orderService.createOrder(orderDto, authentication.getName());
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Get my orders", description = "Customer can view their own orders")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getMyOrders(Authentication authentication) {
        List<OrderResponseDto> orders = orderService.getMyOrders(authentication.getName());
        return ResponseEntity.ok(orders);
    }
}
