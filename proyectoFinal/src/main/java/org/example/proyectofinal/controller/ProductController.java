package org.example.proyectofinal.controller;

import jakarta.validation.Valid;
import lombok.Getter;
import org.example.proyectofinal.dto.request.ProductRequest;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.service.ProductService;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //TODO: Validar que todos los usuarios que acceden aqui sean ADMIN
    public ProductController(ProductService productService, ModelMapper modelMapper, CustResponseBuilder custResponseBuilder) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.custResponseBuilder = custResponseBuilder;
    }

    //===================================== API =========================================

    //TODO: Hacer con filtros de busqueda
    @GetMapping("api/v1/products")
    private ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.findAllProducts();
//      List<Product> productsResponse = Arrays.asList(modelMapper.map(products, CompanyResponse[].class));
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), products);

        return response;
    }

    @GetMapping("api/v1/products/{id}")
    private ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.findOneById(id);
//        CompanyResponse companyResponse = modelMapper.map(company, CompanyResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), product);

        return response;
    }

    @PostMapping("api/v1/products")
    private ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
//        CompanyResponse companyResponse = modelMapper.map(company, CompanyResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), product);

        return response;
    }

    @DeleteMapping("api/v1/products/{id}")
    private ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Boolean bool = productService.deleteProductById(id);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), bool);

        return response;
    }

    @PutMapping("api/v1/products/{id}")
    private ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), product);

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
