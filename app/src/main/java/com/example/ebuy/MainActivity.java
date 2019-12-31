package com.example.ebuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

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
    private TextView totalAmount;
    private static final String TAG = "debugging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iApi = ApiClient.getClient().create(IApi.class);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.product_recycler_view);
        totalAmount = findViewById(R.id.total_amount_textview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.scan_icon) {
            openScanner(new View(this));
        }
        return super.onOptionsItemSelected(item);
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
                if (answer.body() != null) {
                    Log.e(TAG, "onResponse: product is " + answer.body());
                    addProductToRecyclerView(answer.body());
                    totalAmount.setText(String.format(Locale.GERMANY, "%.2f", getTotalAmount() ));
                } else {
                    Toast.makeText(MainActivity.this, "no product was found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return new Product();
    }

    private double getTotalAmount() {
        double sum = 0;
        for (Double pPrice : mPrice) {
            sum += pPrice;
        }
        return sum;
    }


    public void generateQRCode(View view) {

    }


}