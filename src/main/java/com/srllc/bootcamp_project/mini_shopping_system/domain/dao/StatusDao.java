package com.srllc.bootcamp_project.mini_shopping_system.domain.dao;

import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusDao extends JpaRepository<Status, Long> {
    Status findByStatusName(String statusName);
}
