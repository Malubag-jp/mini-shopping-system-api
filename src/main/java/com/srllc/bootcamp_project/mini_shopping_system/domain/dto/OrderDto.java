package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.OrderItemDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private List<OrderItemDto> items;
}
