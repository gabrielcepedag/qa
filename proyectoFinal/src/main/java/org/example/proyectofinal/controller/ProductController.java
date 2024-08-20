package org.example.proyectofinal.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.proyectofinal.dto.request.ProductRequest;
import org.example.proyectofinal.dto.response.ProductResponse;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.service.ProductService;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {
    private final ProductService productService;
    private ModelMapper modelMapper;
    private CustResponseBuilder custResponseBuilder;

    public ProductController(ProductService productService, ModelMapper modelMapper, CustResponseBuilder custResponseBuilder) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.custResponseBuilder = custResponseBuilder;
    }

    //===================================== API =========================================

    //TODO: Hacer con filtros de busqueda
    @GetMapping("api/v1/products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        List<ProductResponse> productResponses = Arrays.asList(modelMapper.map(products, ProductResponse[].class));
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productResponses);

        return response;
    }

    @GetMapping("api/v1/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.findOneById(id);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productResponse);

        return response;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") //El metodo debe ser public
    @PostMapping("api/v1/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productResponse);

        return response;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')") //El metodo debe ser public
    @DeleteMapping("api/v1/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Boolean bool = productService.deleteProductById(id);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), bool);

        return response;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("api/v1/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productResponse);

        return response;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    @PostMapping("api/v1/products/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestParam @NotNull Integer cant) {
        Product product = productService.updateStock(id, cant);
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productResponse);

        return response;
    }

    //=================== VIEWS ==========================
    @GetMapping("/products")
    public String manageProductsPage(Model model){
        List<Product> products = productService.findAllProducts();

        model.addAttribute("productList", products);
        return "manageProducts";
    }


}
