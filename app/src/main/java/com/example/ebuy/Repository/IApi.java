package com.example.ebuy.Repository;

import com.example.ebuy.Model.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApi {

    @GET("/api/Products/{upc}")
    Call<Product> getProduct(@Path("upc") long upc);
}
