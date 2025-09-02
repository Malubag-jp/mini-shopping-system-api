package com.srllc.bootcamp_project.mini_shopping_system.domain.dao;

import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {

}
