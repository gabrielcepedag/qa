package org.example.proyectofinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectofinal.cons.Category;
import org.example.proyectofinal.cons.ERole;
import org.hibernate.envers.Audited;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
}
