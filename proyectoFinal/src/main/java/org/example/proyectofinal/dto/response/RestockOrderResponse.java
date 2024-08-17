package org.example.proyectofinal.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectofinal.entity.Product;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestockOrderResponse {
    private Long id;
    private LocalDateTime creationDate;
    private boolean pending;
    private ProductResponse product;
}
