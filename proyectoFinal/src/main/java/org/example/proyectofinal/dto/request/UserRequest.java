package org.example.proyectofinal.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectofinal.cons.ERole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Pattern(regexp = "^(ADMIN|USER|EMPLOYEE)$", message = "Role must be one of: ADMIN, USER, EMPLOYEE")
    private String role;
}
