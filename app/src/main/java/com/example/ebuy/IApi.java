package com.example.ebuy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApi {

    @GET("/api/Products")
    Call<List<Product>> getAllProducts();

    @GET("/api/Products/{upc}")
    Call<Product> getProduct(@Path("upc") long upc);
}
