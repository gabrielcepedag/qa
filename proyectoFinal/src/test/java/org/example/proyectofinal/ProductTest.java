package org.example.proyectofinal;


import org.example.proyectofinal.cons.Category;
import org.example.proyectofinal.dto.request.ProductRequest;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.exception.BadRequestException;
import org.example.proyectofinal.exception.ResourceNotFoundException;
import org.example.proyectofinal.repository.ProductRepository;
import org.example.proyectofinal.service.ProductService;
import org.example.proyectofinal.service.RestockOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

//TODO: Hacer test para los roles

@DataJpaTest
public class ProductTest {
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    private RestockOrderService restockOrderService;
    private ModelMapper modelMapper;
    private ProductRequest productRequest;
    private ProductRequest productRequest2;
    private Product product;

    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        productService = new ProductService(modelMapper, productRepository, restockOrderService);

        productRequest = ProductRequest
                .builder()
                .name("Product Request")
                .description("Description Request")
                .price(800.00)
                .quantity(5)
                .category(Category.FOOD.getValue())
                .build();
        productRequest2 = ProductRequest
                .builder()
                .name("Product Request 2")
                .description("Description Request 2")
                .price(1500.00)
                .quantity(50)
                .category(Category.SNACK.getValue())
                .build();
        product = Product
                .builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(1000.00)
                .deleted(false)
                .category(Category.DRINK)
                .build();
    }

    @Test
    public void testValidProductRequest(){
        ProductRequest pr1 = new ProductRequest("", "Description Request", 800.00, 5, Category.FOOD.getValue());
        ProductRequest pr2 = new ProductRequest("asdad", "Description Request", -50.00, 5, Category.FOOD.getValue());
        ProductRequest pr3 = new ProductRequest("", "Description Request", 800.00, -5, Category.FOOD.getValue());
        ProductRequest pr4 = new ProductRequest("", "Description Request", 800.00, 5, "Another String");

        assertThrows(BadRequestException.class, () -> productService.createProduct(pr1));
        assertThrows(BadRequestException.class, () -> productService.createProduct(pr2));
        assertThrows(BadRequestException.class, () -> productService.createProduct(pr3));
        assertThrows(BadRequestException.class, () -> productService.createProduct(pr4));
    }

    @Test
    public void testAddProduct() {
        Product response = productService.createProduct(productRequest);
        productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals(1L, product.getId());
        assertEquals(2, productService.findAllProducts().size());
    }

    @Test
    public void testFindOneById_Success() {
        Product prod = productService.createProduct(productRequest);
        Product product = productService.findOneById(prod.getId());
        assertEquals(prod.getId(), (long) product.getId());
    }

    @Test
    public void testFindAllProducts() {
        productService.createProduct(productRequest);
        productService.createProduct(productRequest2);
        List<Product> products = productService.findAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void testDeleteLogicProductById() {
        Product prod = productService.createProduct(productRequest);
        productService.createProduct(productRequest2);
        boolean result = productService.deleteProductById(prod.getId());

        assertTrue(result);
        assertTrue(productRepository.findById(prod.getId()).get().isDeleted());
        assertThrows(ResourceNotFoundException.class, () -> productService.findOneById(prod.getId()));
        assertEquals(1, productService.findAllProducts().size());
    }

    @Test
    public void testUpdateProduct() {
        Product prod = productService.createProduct(productRequest);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Updated Product");
        productRequest.setDescription("This is an updated product");
        productRequest.setPrice(150.0);
        productRequest.setQuantity(20);

        Product updatedProduct = productService.updateProduct(prod.getId(), productRequest);
        Product p1 = productService.findOneById(prod.getId());

        assertEquals(updatedProduct.getId(), p1.getId());
        assertEquals(p1.getName(), productRequest.getName());
        assertEquals(p1, updatedProduct);
    }
}
