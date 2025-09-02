package com.srllc.bootcamp_project.mini_shopping_system.config.init;


import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.RoleDao;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationRunner {
    private final RoleDao roleDao;

    private void createRoleIfNotExists(String roleName) {
        if (roleDao.findByRoleName(roleName) == null) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleDao.save(role); // save into the database
            log.info("Role '{}' created successfully!", roleName);
        } else {
            log.info("Role '{}' already exist", roleName);
        }
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("CUSTOMER");
    }
}
