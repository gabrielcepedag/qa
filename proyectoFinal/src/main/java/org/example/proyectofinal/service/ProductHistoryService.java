package org.example.proyectofinal.service;

import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.ProductHistory;
import org.example.proyectofinal.entity.User;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.repository.ProductHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductHistoryService {
    private final ProductHistoryRepository productHistoryRepository;
    private final UserService userService;

    public ProductHistoryService(ProductHistoryRepository productHistoryRepository, UserService userService) {
        this.productHistoryRepository = productHistoryRepository;
        this.userService = userService;
    }

    public List<ProductHistory> getAllProductHistory() {
        try {
            return productHistoryRepository.findAllOrderByDate();
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public List<ProductHistory> findAllByProductId(Long productId) {
        try {
            return productHistoryRepository.findAllByProductId(productId);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public void createHistory(Product product, int quantity) {

        User user = userService.getUserLoggued();

        ProductHistory productHistory = ProductHistory
                .builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .quantityBefore(product.getQuantity())
                .quantityAfter(quantity)
                .userId(user.getId())
                .userName(user.getName())
                .userUsername(user.getUsername())
                .date(new Date())
                .build();

        try {
            productHistoryRepository.save(productHistory);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
