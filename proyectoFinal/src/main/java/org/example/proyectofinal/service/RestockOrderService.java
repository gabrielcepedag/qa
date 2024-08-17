package org.example.proyectofinal.service;

import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.RestockOrder;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.repository.ProductRepository;
import org.example.proyectofinal.repository.RestockOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestockOrderService {
    private final RestockOrderRepository restockOrderRepository;
    private ModelMapper modelMapper;

    public RestockOrderService(RestockOrderRepository restockOrderRepository, ModelMapper modelMapper) {
        this.restockOrderRepository = restockOrderRepository;
        this.modelMapper = modelMapper;
    }

    public void create(Product product) {
        try {
            List<RestockOrder> ordersByProduct = restockOrderRepository.findAllByProductIdAndPendingIsTrue(product.getId());
            System.out.println("Ordenes Pendientes -> " + ordersByProduct.size());
            if (ordersByProduct.isEmpty()) {
                RestockOrder restockOrder = new RestockOrder();
                restockOrder.setProduct(product);
                product.addRestockOrder(restockOrder);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), e.getLocalizedMessage());
        }
    }
}
