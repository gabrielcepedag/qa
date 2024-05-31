package org.example;

import org.example.exceptions.NonNegativePrice;
import org.example.exceptions.ProductNotFoundException;
import org.example.model.Cart;
import org.example.model.Product;
import org.example.service.MainServices;
import org.junit.Test;
import junit.framework.TestCase;

import static org.junit.Assert.assertThrows;

public class TestProduct extends TestCase{

    public void testCreateProductId(){
        Product p1 = new Product("as", "sa", 900);
        Product p2 = new Product("asa", "sas", 910);
        assertEquals(2, p2.getId());
    }

    public void testAddProductToCart() throws ProductNotFoundException {
        Cart cart = new Cart();
        Product p1 = new Product("as", "sa", 900);
        MainServices.newProduct(p1);
        MainServices.addProductToCart(cart, "as", 10);
        assertEquals(1, cart.getQuantity());
    }

    public void testProductNotFound(){
        Cart cart = new Cart();
        assertThrows(ProductNotFoundException.class, () -> MainServices.addProductToCart(cart, "assadasd", 11));
    }

    public void testDeleteItem() throws ProductNotFoundException {
        Cart cart = new Cart();
        Product p1 = new Product("as", "sa", 900);
        MainServices.newProduct(p1);
        MainServices.addProductToCart(cart, "as", 10);
        MainServices.deleteItem(cart, "as");
        assertEquals(0, cart.getQuantity());
    }

    public void testModifyQuantity() throws ProductNotFoundException {
        Cart cart = new Cart();
        Product p1 = new Product("as", "sa", 900);
        MainServices.newProduct(p1);
        MainServices.addProductToCart(cart, "as", 10);
        MainServices.modifyQuantity(cart, "as", 5);
        assertEquals(5, cart.getCartItems().get(0).getQuantity());
    }

    public void testCalcTotal() throws ProductNotFoundException {
        Cart cart = new Cart();
        Product p1 = new Product("ab", "p1", 100);
        Product p2 = new Product("cd", "p2", 200);
        Product p3 = new Product("ef", "p3", 300);
        MainServices.newProduct(p1);
        MainServices.newProduct(p2);
        MainServices.newProduct(p3);

        MainServices.addProductToCart(cart, "ab", 5);
        MainServices.addProductToCart(cart, "cd", 3);
        assertEquals((float)1100, MainServices.calcTotal(cart));
    }

    public void testNonNegativePrice(){
        assertThrows(NonNegativePrice.class, () -> MainServices.newProduct(new Product("as", "as", -5)));
        Product product = new Product("as", "as", 100);
        assertThrows(NonNegativePrice.class, () -> product.setPrice(-10));
    }

}
