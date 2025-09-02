package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "userNameOrEmail is required!")
    @Schema(example = "johndoe1@gmail.com")
    private String userNameOrEmail;

    @NotBlank(message = "Password is required!")
    @Schema(example = "abc123")
    private String password;
}
