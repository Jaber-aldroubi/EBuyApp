package com.example.ebuy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class QRCodeGenerator extends AppCompatActivity {
    private static final String TAG = "Debugging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: in next intent ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);
        Intent intent = getIntent();
        String mUpc = intent.getStringExtra("UPC_LIST");
        Log.d(TAG, "onCreate: " + mUpc);

        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(mUpc, BarcodeFormat.QR_CODE, 400, 400);
            ImageView imageViewQrCode = findViewById(R.id.qrCode);
            imageViewQrCode.setImageBitmap(bitmap);
        } catch (Exception e) {

        }
    }
}