package org.example.proyectofinal.cons;

import lombok.Getter;

@Getter
public enum ERole {
    USER("ROLE_USER"),
    OWNER("ROLE_EMPLOYEE"),
    ADMIN("ROLE_ADMIN");
    private final String value;
    ERole(String value) {
        this.value = value;
    }
}
