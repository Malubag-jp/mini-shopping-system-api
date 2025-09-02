package com.srllc.bootcamp_project.mini_shopping_system.domain.service;


import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.AuthResponseDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.LoginDto;
import com.srllc.bootcamp_project.mini_shopping_system.domain.dto.UserRegistrationDto;

public interface AuthService {
    String userRegistration(UserRegistrationDto userRegistrationDto);
    AuthResponseDto login(LoginDto loginDto);
}
