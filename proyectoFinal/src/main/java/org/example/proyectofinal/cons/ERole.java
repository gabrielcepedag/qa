package org.example.proyectofinal.cons;

import lombok.Getter;

@Getter
public enum ERole {
    USER("USER"),
    EMPLOYEE("EMPLOYEE"),
    ADMIN("ADMIN");
    private final String value;
    ERole(String value) {
        this.value = value;
    }
}
