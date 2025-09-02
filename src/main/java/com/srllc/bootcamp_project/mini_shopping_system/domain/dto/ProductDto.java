package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "Product name is required!")
    @Schema(example = "Laptop")
    private String productName;

    @Schema(example = "High-performance laptop")
    private String description;

    @NotNull(message = "Price is required!")
//    @Positive(message = "Price must be positive!")
    @Schema(example = "999.99")
    private Double price;

    @NotNull(message = "Stock is required!")
    @Schema(example = "50")
    private Integer stock;
}
