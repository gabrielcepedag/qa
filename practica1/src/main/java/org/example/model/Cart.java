package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cartItems;
    private int quantity;
    private float total;

    public Cart() {
        cartItems = new ArrayList<>();
        quantity = 0;
        total = 0;
    }

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        quantity = cartItems.size();
        total = getTotal();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getQuantity() {
        return cartItems.size();
    }

    public float getTotal() {
        for (CartItem item : cartItems) {
            total += (item.getProduct().getPrice() * item.getQuantity());
        }
        return total;
    }

    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    public void remove(CartItem item) {
        cartItems.remove(item);
    }
}
