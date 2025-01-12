package com.example.WombatFm.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    Integer userId;

    @Size(min = 4, max = 30, message = "Username must be at least 4 characters")
    String username;

    @Size(min = 4, max = 60, message = "Password must be at least 4 characters")
    String password;

    @NotBlank(message = "Confirm password must be filled")
    String confirmPassword;

    String role;

    Boolean isActive;
}
