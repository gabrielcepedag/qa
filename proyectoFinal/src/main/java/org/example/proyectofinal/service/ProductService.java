package org.example.proyectofinal.service;

import org.example.proyectofinal.cons.ERole;
import org.example.proyectofinal.dto.request.ProductRequest;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.ProductHistory;
import org.example.proyectofinal.entity.User;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.exception.ResourceNotFoundException;
import org.example.proyectofinal.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private RestockOrderService restockOrderService;
    private ModelMapper modelMapper;
    private ProductHistoryService productHistoryService;

    public ProductService(ProductRepository productRepository, RestockOrderService restockOrderService, ModelMapper modelMapper, ProductHistoryService productHistoryService) {
        this.productRepository = productRepository;
        this.restockOrderService = restockOrderService;
        this.modelMapper = modelMapper;
        this.productHistoryService = productHistoryService;
    }

    public Product findOneById(Long id) {
        Product product =  productRepository.findById(id).orElse(null);
        if (product == null || product.isDeleted()) {
            throw new ResourceNotFoundException(Product.class.getSimpleName(), "ID", id);
        }
        return product;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAllByDeletedIsFalse();
    }

    public Product createProduct(ProductRequest productRequest) {
//        if (!(user.getRole().equals(ERole.ADMIN) || user.getRole().equals(ERole.OWNER))) {
//            throw new UnauthorizedException();
//        }
//        TODO: SOLO USUARIOS ADMIN Y EMPLOYEE (INTENTAR USAR EL PREAUTHORIZE)
        try {
            Product product = new Product();
            product.setId(null);
            modelMapper.map(productRequest, product);
            return productRepository.save(product);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public boolean deleteProductById(Long id) {
        Product product = findOneById(id);
        product.setDeleted(true);
        productRepository.save(product);
        return true;
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = findOneById(id);

        modelMapper.map(productRequest, product);
        product.setId(id);
        if (!productRequest.getQuantity().equals(product.getQuantity())){
            productHistoryService.createHistory(product, productRequest.getQuantity());
        }
        return productRepository.save(product);
    }




    public Product updateStock(Long id, Integer cant) {
        Product product = findOneById(id);

        try {
            if (product.getQuantity() + cant >= 0){
                product.setQuantity(product.getQuantity() + cant);
                checkQuantity(product);
                productHistoryService.createHistory(product, cant);
            }else{
                throw new BadRequestException("Quantity can't be less than 0");
            }

            return productRepository.save(product);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private void checkQuantity(Product product) {
        if (product.getQuantity() <= product.getMinQuantity()) {
            System.out.println("Se va a crear una order de compra -> ProductQuantity: "+product.getQuantity()+ " - MinQuantity: "+product.getMinQuantity());
            restockOrderService.create(product);
        }
    }
}
