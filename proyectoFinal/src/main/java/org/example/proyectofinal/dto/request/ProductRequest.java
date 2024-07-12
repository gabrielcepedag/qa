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
public class ProductRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @PositiveOrZero
    private Double price;
    @PositiveOrZero
    private Integer quantity;
    @Pattern(regexp = "^(DRINK|FOOD|SNACK)$", message = "Category must be one of: DRINK, FOOD, SNACK")
    private String category;
}
