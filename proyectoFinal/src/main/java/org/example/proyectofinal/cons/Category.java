package org.example.proyectofinal.cons;

import lombok.Getter;

@Getter
public enum Category {
    DRINK("DRINK"),
    FOOD("FOOD"),
    SNACK("SNACK");

    private final String value;

    Category(String value) {
        this.value = value;
    }
}
