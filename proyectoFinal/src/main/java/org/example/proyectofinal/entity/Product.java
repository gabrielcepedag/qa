package org.example.proyectofinal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
    @NotBlank
    private String name;
    private String description;
    @NotNull
    @PositiveOrZero
    private Double price;
    @PositiveOrZero
    private Integer quantity;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;


    //    private String name;
//    private String description;
//    private Double price;
//    private Integer quantity;
//    @Column(columnDefinition = "boolean default false")
//    private boolean deleted;
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Category category;
}
