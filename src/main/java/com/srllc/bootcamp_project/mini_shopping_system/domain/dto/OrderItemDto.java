package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    @NotNull(message = "Product ID is required!")
    private Long productId;

    @NotNull(message = "Quantity is required!")
    private Integer quantity;
}
