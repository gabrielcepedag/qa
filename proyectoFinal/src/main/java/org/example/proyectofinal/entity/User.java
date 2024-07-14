package org.example.proyectofinal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.proyectofinal.cons.ERole;
import org.hibernate.envers.Audited;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @Column(nullable = false)
    @NotBlank
    private String username;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @Column(columnDefinition = "boolean default false")
    private boolean deleted;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;

    // Este método se ejecuta antes de que se inserte la entidad en la base de datos
    @PrePersist
    protected void onCreate() {
        System.out.println("Entro al onCreate con role " + role);
        if (role == null) {
            role = ERole.USER;
        }
    }
}
