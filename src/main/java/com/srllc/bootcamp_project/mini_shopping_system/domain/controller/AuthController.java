package com.srllc.bootcamp_project.mini_shopping_system.domain.controller;


import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.AuthResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.LoginDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.UserRegistrationDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Operations for managing authentication")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "User registration", description = "allows the user to register new account")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        String response = authService.userRegistration(userRegistrationDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User authentication", description = "allows the user to authenticate and get a valid bearer token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        AuthResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }
}
