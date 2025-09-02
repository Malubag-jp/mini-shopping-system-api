package com.srllc.bootcamp_project.mini_shopping_system.domain.service.Impl;


import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.RoleDao;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dao.UserDao;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.AuthResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.LoginDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.UserRegistrationDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.Role;
import com.srllc.bootcamp_project.mini_shopping_system.domain.entity.User;
import com.srllc.bootcamp_project.mini_shopping_system.domain.service.AuthService;
import com.srllc.bootcamp_project.mini_shopping_system.security.jwt.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @Override
    public String userRegistration(UserRegistrationDto userRegistrationDto) {
        log.info("Registering new user: {}", userRegistrationDto.getUserName());
        validateUser(userRegistrationDto);
        User user = new User();
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setLastName(userRegistrationDto.getLastName());
        user.setEmail(userRegistrationDto.getEmail());
        user.setUserName(userRegistrationDto.getUserName());

        // password encoded
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        // assign the default role
        Role userRole = roleDao.findByRoleName("CUSTOMER");
        if (userRole == null) {
            log.error("Default role 'ROLE_CUSTOMER' not found in the database");
            throw  new IllegalArgumentException("Customer role not found. Please check the database setup!");
        }
        user.setRoles(Collections.singleton(userRole));

        // save into the database
        userDao.save(user);
        log.info("USer: {} registered successfully! ", user.getUserName());
        return "User registered successfully!";
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);


        log.info("User {} logged-in successfully!", loginDto.getUserNameOrEmail());
        return new AuthResponseDto("User Logged-in successfully!", token);
    }

    // validate if email or username already exist
    private void validateUser(UserRegistrationDto userRegistrationDto) {
        if (Boolean.TRUE.equals(userDao.existsByUserName(userRegistrationDto.getUserName()))) {
            log.warn("Username: {} is already taken.", userRegistrationDto.getUserName());
            throw new IllegalArgumentException("Username already exist!");

        }
        if (Boolean.TRUE.equals(userDao.existsByEmail(userRegistrationDto.getEmail()))) {
            log.warn("Email: {} is already taken.", userRegistrationDto.getEmail());
            throw new IllegalArgumentException("Email already exist!");

        }
    }
}
