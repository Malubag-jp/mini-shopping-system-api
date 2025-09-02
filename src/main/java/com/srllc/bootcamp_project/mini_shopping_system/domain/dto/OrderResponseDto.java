package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Long id;
    private String customerName;
    private String customerEmail;
    private Double totalAmount;
    private String status;
    private List<OrderItemResponseDto> items;
    private LocalDateTime dateCreated;
    private LocalDateTime dateModified;
}
