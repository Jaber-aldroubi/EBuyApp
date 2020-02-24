package com.example.ebuy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ebuy.Model.Product;

import java.util.ArrayList;

public class ProductViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Product>> mProducts;

    public LiveData<ArrayList<Product>> getProducts (){

        return mProducts;
    }
}
