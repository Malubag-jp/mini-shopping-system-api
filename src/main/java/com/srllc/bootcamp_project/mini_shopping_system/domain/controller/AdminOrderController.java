package com.srllc.bootcamp_project.mini_shopping_system.domain.controller;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.OrderResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Order Management", description = "Admin operations for order management")
@SecurityRequirement(name = "bearerAuth")
public class AdminOrderController {

    private final OrderService orderService;

    @Operation(summary = "Get all orders", description = "Admin can view all orders from all customers")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Update order status", description = "Admin can update order status")
    @PutMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        OrderResponseDto order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(order);
    }
}
