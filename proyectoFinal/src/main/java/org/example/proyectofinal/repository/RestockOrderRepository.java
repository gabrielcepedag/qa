package org.example.proyectofinal.repository;

import org.example.proyectofinal.entity.RestockOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestockOrderRepository extends JpaRepository<RestockOrder, Long> {
    List<RestockOrder> findAllByProductIdAndPendingIsTrue(Long productId);

    List<RestockOrder> findAllByProductId(Long id);

    List<RestockOrder> findAllByPending(Boolean pending);
}
