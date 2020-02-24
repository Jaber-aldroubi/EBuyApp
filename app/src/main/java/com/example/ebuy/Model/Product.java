package com.example.ebuy.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Product {

    private long upc;
    private String productDescription;
    private String category;
    private double price;
    private String brand;


    private int productCounter;

    public Product(long upc, String productDescription, String category, double price, String brand, int productCounter) {
        this.upc = upc;
        this.productDescription = productDescription;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.productCounter = productCounter;    }

    public Product() {
    }

    protected Product(Parcel in) {
        upc = in.readLong();
        productDescription = in.readString();
        category = in.readString();
        price = in.readDouble();
        brand = in.readString();
    }

    public long getId() {
        return upc;
    }

    public void setId(long upc) {
        this.upc = upc;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getProductCounter() {
        return productCounter;
    }

    public void setProductCounter(int productCounter) {
        this.productCounter = productCounter;
    }


    @Override
    public String toString() {
        return "Product{" +
                "upc=" + upc +
                ", productDescription='" + productDescription + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", brand='" + brand + '\'' +
                '}';
    }
    
}
