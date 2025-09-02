package com.srllc.bootcamp_project.mini_shopping_system.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotBlank(message = "First name cannot be Blank!")
    @Schema(example = "John")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Schema(example = "Doe")
    private String lastName;

    @NotBlank(message = "Username is required!")
    @Schema(example = "john_doe")
    private String userName;

    @NotBlank(message = "Email is required!")
    @Schema(example = "johndoe1@gmail.com")
    private String email;

    @NotBlank(message = "Password is required!")
    @Schema(example = "abc123")
    private String password;
}
