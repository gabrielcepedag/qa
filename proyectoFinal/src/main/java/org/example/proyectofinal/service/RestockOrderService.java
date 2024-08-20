package org.example.proyectofinal.service;

import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.RestockOrder;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.exception.ResourceNotFoundException;
import org.example.proyectofinal.repository.ProductRepository;
import org.example.proyectofinal.repository.RestockOrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestockOrderService {
    private final RestockOrderRepository restockOrderRepository;
    private final ProductService productService;

    public RestockOrderService(RestockOrderRepository restockOrderRepository, @Lazy ProductService productService) {
        this.restockOrderRepository = restockOrderRepository;
        this.productService = productService;
    }

    public void create(Product product) {
        try {
            List<RestockOrder> ordersByProduct = restockOrderRepository.findAllByProductIdAndPendingIsTrue(product.getId());
            System.out.println("Ordenes Pendientes -> " + ordersByProduct.size());
            if (ordersByProduct.isEmpty()) {
                RestockOrder restockOrder = new RestockOrder();
                restockOrder.setProduct(product);
                restockOrder.setPending(true);
                product.addRestockOrder(restockOrder);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage(), e.getLocalizedMessage());
        }
    }

    public List<RestockOrder> findAllRestock(Boolean pending) {
        if (pending == null){
            return restockOrderRepository.findAll();
        }else{
            return restockOrderRepository.findAllByPending(pending);
        }
    }

    public RestockOrder updateRestock(Long restockId, Integer quantity) {
        RestockOrder restockOrder = findById(restockId);
        Product product = productService.updateStock(restockOrder.getProduct().getId(), quantity);

        if (product.getQuantity() > product.getMinQuantity()){
            restockOrder.setPending(false);
            return restockOrderRepository.save(restockOrder);
        }
        return restockOrder;
    }

    public RestockOrder findById(Long restockId) {
        Optional<RestockOrder> restockOrder = restockOrderRepository.findById(restockId);
        if (restockOrder.isPresent()){
            if (restockOrder.get().isPending()){
                return restockOrder.get();
            }
        }
        throw new ResourceNotFoundException(RestockOrder.class.getSimpleName(), "ID", restockId);
    }
}
