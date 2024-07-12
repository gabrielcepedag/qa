package org.example.proyectofinal.repository;

import org.example.proyectofinal.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByDeletedIsFalse();
}
