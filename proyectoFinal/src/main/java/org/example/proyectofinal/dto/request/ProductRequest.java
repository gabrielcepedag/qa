package org.example.proyectofinal.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import org.example.proyectofinal.cons.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @PositiveOrZero
    private Double price;
    @PositiveOrZero
    private Integer quantity;
    @PositiveOrZero
    private Integer minQuantity;
    @Pattern(regexp = "^(DRINK|FOOD|SNACK)$", message = "Category must be one of: DRINK, FOOD, SNACK")
    private String category;

    public ProductRequest(String name, String description, double price, int quantity, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }
}
