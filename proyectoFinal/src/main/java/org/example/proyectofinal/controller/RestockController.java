package org.example.proyectofinal.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.proyectofinal.dto.response.ProductResponse;
import org.example.proyectofinal.dto.response.RestockOrderResponse;
import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.RestockOrder;
import org.example.proyectofinal.service.ProductService;
import org.example.proyectofinal.service.RestockOrderService;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RestockController {
    private final RestockOrderService restockOrderService;
    private ModelMapper modelMapper;
    private CustResponseBuilder custResponseBuilder;
    private final ProductService productService;

    public RestockController(RestockOrderService restockOrderService, ModelMapper modelMapper, CustResponseBuilder custResponseBuilder, ProductService productService) {
        this.restockOrderService = restockOrderService;
        this.modelMapper = modelMapper;
        this.custResponseBuilder = custResponseBuilder;
        this.productService = productService;
    }

    @GetMapping("api/v1/restock")
    public ResponseEntity<?> getAllRestockOrders(@RequestParam(required = false) Boolean pending) {
        List<RestockOrder> restockOrders = restockOrderService.findAllRestock(pending);
        List<RestockOrderResponse> orders = Arrays.asList(modelMapper.map(restockOrders, RestockOrderResponse[].class));
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), orders);

        return response;
    }

    @PutMapping("api/v1/restock/{restockId}")
    public ResponseEntity<?> updateRestock(@PathVariable Long restockId, @Valid @RequestParam @Positive Integer quantity) {
        RestockOrder restockOrder = restockOrderService.updateRestock(restockId, quantity);
        RestockOrderResponse restockOrderResponse = modelMapper.map(restockOrder, RestockOrderResponse.class);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), restockOrderResponse);

        return response;
    }

    //=================== VIEWS ==========================
    @GetMapping("/stock")
    public String manageStockPage(Model model){
        List<Product> products = productService.findAllProducts();
        model.addAttribute("productList", products);

        return "manageStock";
    }

    @GetMapping("/stock/restock")
    public String manageRestockPage(Model model){
        List<RestockOrder> restockOrders = restockOrderService.findAllRestock(true);

        model.addAttribute("restockOrdersList", restockOrders);

        return "manageRestockOrders";
    }

    @GetMapping("/stock/history")
    public String manageInventoryMovementsPage(Model model){
        List<Object> movementsList = new ArrayList<>();

        model.addAttribute("movementsList", movementsList);

        return "manageMovements";
    }
}
