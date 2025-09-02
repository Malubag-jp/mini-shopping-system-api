package com.srllc.bootcamp_project.mini_shopping_system.config.init;

import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.StatusDao;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class StatusInitializer implements ApplicationRunner {
    private final StatusDao statusDao;

    private void createStatusIfNotExists(String statusName) {
        if (statusDao.findByStatusName(statusName) == null) {
            Status status = new Status();
            status.setStatusName(statusName);
            statusDao.save(status);
            log.info("Status '{}' created successfully!", statusName);
        } else {
            log.info("Status '{}' already exists", statusName);
        }
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createStatusIfNotExists("PENDING");
        createStatusIfNotExists("CONFIRMED");
        createStatusIfNotExists("SHIPPED");
        createStatusIfNotExists("DELIVERED");
        createStatusIfNotExists("CANCELLED");
    }
}