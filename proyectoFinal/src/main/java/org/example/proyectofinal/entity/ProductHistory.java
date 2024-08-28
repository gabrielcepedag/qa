package org.example.proyectofinal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "inventory_movements")
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private String productDescription;
    private int quantityBefore;
    private int quantityAfter;

    private Long userId;
    private String userName;
    private String userUsername;
    private String role;

    private Date date;

}
