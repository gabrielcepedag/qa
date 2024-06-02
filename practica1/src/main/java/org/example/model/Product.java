package org.example.model;

import org.example.exceptions.NonNegativePrice;

public class Product {
    private static int staticID = 1;
    private int id;
    private String code;
    private String name;
    private float price;

    public Product(String code, String name, float price) {
        if (price < 0){
            throw new NonNegativePrice();
        }
        System.out.println("ID: "+Product.staticID);
        this.id = Product.staticID++;
//        Product.staticID += 1;
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        if (price < 0){
            throw new NonNegativePrice();
        }
        this.price = price;
    }
}
