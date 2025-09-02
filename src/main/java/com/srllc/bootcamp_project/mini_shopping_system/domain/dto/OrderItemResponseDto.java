package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    private String productName;
    private Double productPrice;
    private Integer quantity;
    private Double price;
    private Double totalPrice; // quantity * price
}