package com.example.ebuy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductRecyclerViewHolder> {

    private ArrayList<String> mBrands;
    private ArrayList<Double> mPrices;
    private ArrayList<String> mDescriptions;
    private Context context;


    public ProductRecyclerViewAdapter(Context context, ArrayList<String> mDescriptions, ArrayList<String> mBrands, ArrayList<Double> mPrices) {
        this.context = context;
        this.mDescriptions = mDescriptions;
        this.mBrands = mBrands;
        this.mPrices = mPrices;
    }

    @NonNull
    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_item, parent, false);
        return new ProductRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewHolder holder, int position) {
        holder.Description.setText(mDescriptions.get(position));
        holder.Brand.setText(mBrands.get(position));
        holder.Price.setText(String.format(Locale.GERMANY,"%.2f", mPrices.get(position))+"€" );
    }

    @Override
    public int getItemCount() {
        return mDescriptions.size();
    }

    public static class ProductRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView Description;
        TextView Price;
        TextView Brand;
        ConstraintLayout LayoutForItem;

        public ProductRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            Description = itemView.findViewById(R.id.description_textview);
            Brand = itemView.findViewById(R.id.brand_textview);
            Price = itemView.findViewById(R.id.price_textview);
            LayoutForItem = itemView.findViewById(R.id.item_layout);
        }
    }
}
