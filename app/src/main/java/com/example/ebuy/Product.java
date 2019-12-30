package com.example.ebuy;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private long upc;

    private String productDescription;

    private String category;

    private double price;
    private String brand;

    public Product(long upc, String productDescription, String category, double price, String brand) {
        this.upc = upc;
        this.productDescription = productDescription;
        this.category = category;
        this.price = price;
        this.brand = brand;
    }

    public Product() {
    }

    protected Product(Parcel in) {
        upc = in.readLong();
        productDescription = in.readString();
        category = in.readString();
        price = in.readDouble();
        brand = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(upc);
        dest.writeString(productDescription);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeString(brand);
    }
}
