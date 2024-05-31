package org.example.service;

import org.example.exceptions.ProductNotFoundException;
import org.example.model.Cart;
import org.example.model.CartItem;
import org.example.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainServices {
    static List<Product> productList = new ArrayList<>();
    public static void addProductToCart(Cart cart, String code, int quantity) throws ProductNotFoundException {
        Product p1 = MainServices.productList.stream().filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException());

        CartItem item = new CartItem(p1, quantity);
        cart.addItem(item);
    }

    public static void deleteItem(Cart cart, String code) throws ProductNotFoundException {
        CartItem item = cart.getCartItems().stream().filter(p -> p.getProduct().getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException());

        cart.remove(item);
    }

    public static void modifyQuantity(Cart cart, String code, int quantity) throws ProductNotFoundException {
        CartItem item = cart.getCartItems().stream().filter(p -> p.getProduct().getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException());

        item.setQuantity(quantity);
    }

    public static float calcTotal(Cart cart){
        return cart.getTotal();
    }
    public static void newProduct(Product product){
        MainServices.productList.add(product);
    }
}
