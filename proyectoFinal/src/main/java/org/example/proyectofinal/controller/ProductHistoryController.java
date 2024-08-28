package org.example.proyectofinal.controller;

import org.example.proyectofinal.entity.Product;
import org.example.proyectofinal.entity.ProductHistory;
import org.example.proyectofinal.entity.RestockOrder;
import org.example.proyectofinal.service.ProductHistoryService;
import org.example.proyectofinal.utils.response.ApiResponse;
import org.example.proyectofinal.utils.response.CustResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductHistoryController {
    private final ProductHistoryService productHistoryService;
    private CustResponseBuilder custResponseBuilder;

    public ProductHistoryController(ProductHistoryService productHistoryService, CustResponseBuilder custResponseBuilder) {
        this.productHistoryService = productHistoryService;
        this.custResponseBuilder = custResponseBuilder;
    }

    @GetMapping("api/v1/history")
    public ResponseEntity<?> findAllProductHistory() {
        List<ProductHistory> productHistories = productHistoryService.getAllProductHistory();
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productHistories);

        return response;
    }

    @GetMapping("api/v1/history/{productId}")
    public ResponseEntity<?> findAllProductHistoryByProductId(@PathVariable Long productId) {
        List<ProductHistory> productHistories = productHistoryService.findAllByProductId(productId);
        ResponseEntity<ApiResponse> response = custResponseBuilder.buildResponse(HttpStatus.OK.value(), productHistories);

        return response;
    }

    //=================== VIEWS ==========================
    @GetMapping("/stock/history")
    public String manageInventoryMovementsPage(Model model){
        List<ProductHistory> movementsList = productHistoryService.getAllProductHistory();

        model.addAttribute("movementsList", movementsList);

        return "manageMovements";
    }
}
