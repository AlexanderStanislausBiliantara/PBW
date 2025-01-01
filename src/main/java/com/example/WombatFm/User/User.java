package com.example.WombatFm.User;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    Integer userId;

    @NotNull
    @Size(min=4, max=30)
    String username;

    @NotNull
    @Size(min=4, max=60)
    String password;

    @NotNull
    @Size(min=4, max=60)
    String confirmPassword;

    String role;

    Boolean isActive;
}
