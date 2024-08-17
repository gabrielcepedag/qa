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

import java.util.ArrayList;
import java.util.List;

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
    @PositiveOrZero
    @Column(columnDefinition = "integer default 0")
    private Integer minQuantity;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RestockOrder> restockOrders = new ArrayList<>();

    @PreUpdate
    public void preUpdate() {
        if (quantity <= minQuantity) {
            System.out.println("Se va a crear una order de compra -> ProductQuantity: "+quantity+ " - MinQuantity: "+minQuantity);
            RestockOrder restockOrder = new RestockOrder();
            restockOrder.setProduct(this);
            restockOrders.add(restockOrder);
        }
    }
}
