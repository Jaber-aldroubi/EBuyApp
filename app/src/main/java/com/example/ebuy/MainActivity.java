package com.example.ebuy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
    private ArrayList<Product> mProduct = new ArrayList<>();
    private ArrayList<Long> mUpc = new ArrayList<>();

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
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                long upc = Long.parseLong(result.getContents());
                getProduct(upc, product -> {
                    if (product != null) {
                        addProductToRecyclerView(product);
                        totalAmount.setText(getTotalAmount());
                    } else
                        Toast.makeText(MainActivity.this, "no product was found!", Toast.LENGTH_LONG).show();
                });
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getProduct(long upc, onGetProduct callback) {

        Call<Product> productCall = iApi.getProduct(upc);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, @Nullable Response<Product> response) {
                Product product = response.body();
                callback.getProduct(product);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addProductToRecyclerView(Product product) {
        mUpc.add(product.getId());
        mProduct.add(product);
        adapter = new ProductRecyclerViewAdapter(this, mProduct);
        new ItemTouchHelper(swipeToDelete).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    interface onGetProduct {
        void getProduct(Product product);
    }

    ItemTouchHelper.SimpleCallback swipeToDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Delete Product")
                    .setMessage("Are you really sure you want to delete " + mProduct.get(viewHolder.getAdapterPosition()).getBrand() + " from your shopping list?")

                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        mProduct.remove(viewHolder.getAdapterPosition());
                        totalAmount.setText(getTotalAmount());
                        adapter.notifyDataSetChanged();
                    })

                    .setNegativeButton(android.R.string.no, (dialog, which) -> recyclerView.setAdapter(adapter))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    };

    private String getTotalAmount() {
        double sum = 0;
        for (Product product : mProduct) {
            sum += product.getPrice();
        }
        return String.format(Locale.GERMANY, "%.2f", sum) + " €";
    }

    public void generateQRCode(View view) {
        Log.d(TAG, "generateQRCode:" +  mUpc.toString());
        if (mUpc == null || mUpc.size() == 0) {
            Toast.makeText(this, "no product in the list!!", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, QRCodeGenerator.class);

        intent.putExtra("UPC_LIST", mUpc.toString());

        startActivity(intent);

    }
}