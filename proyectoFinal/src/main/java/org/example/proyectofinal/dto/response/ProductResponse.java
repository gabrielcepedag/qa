package org.example.proyectofinal.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectofinal.cons.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Integer minQuantity;
    private boolean deleted;
    private Category category;
}
