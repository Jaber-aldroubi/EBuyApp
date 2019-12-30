package com.example.ebuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    IApi iApi;
    private static final int RequestCode = 1;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> mDescription = new ArrayList<>();
    private ArrayList<Long> mUpc = new ArrayList<>();
    private ArrayList<String> mBrand = new ArrayList<>();
    private ArrayList<Double> mPrice = new ArrayList<>();
    private static final String TAG = "debugging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iApi = ApiClient.getClient().create(IApi.class);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.product_recycler_view);
    }

    public void openScanner(View view) {
        Intent intent = new Intent(this, ScannerActivity.class);
        startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode) {

            if (resultCode == RESULT_OK) {
                long upc = data.getLongExtra("upc", 0);
                Log.e(TAG, "onActivityResult:  Result is ok and upc is " + upc);

                getProduct(upc);
//                addProductToRecyclerView(product);
            }
        }
    }

    private void addProductToRecyclerView(Product product) {

        mDescription.add(product.getProductDescription());
        mUpc.add(product.getId());
        mBrand.add(product.getBrand());
        mPrice.add(product.getPrice());

        adapter = new ProductRecyclerViewAdapter(this, mDescription, mBrand, mPrice);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Product getProduct(long upc) {

        Call<Product> productCall = iApi.getProduct(upc);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, @Nullable Response<Product> answer) {
                Log.e(TAG, "onResponse: " + answer );
                if (answer.body() != null) {
                    Log.e(TAG, "onResponse: product is " + answer.body());
                    addProductToRecyclerView(answer.body());
                } else {
                    Toast.makeText(MainActivity.this, "no product was found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        return new Product();
    }


    public void generateQRCode(View view) {

    }


}
