package com.srllc.bootcamp_project.mini_shopping_system.domain.dao;

import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
