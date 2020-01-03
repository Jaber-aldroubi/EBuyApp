package com.example.ebuy;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IApi {

    @GET("/api/Products/{upc}")
    Call<Product> getProduct(@Path("upc") long upc);
}
