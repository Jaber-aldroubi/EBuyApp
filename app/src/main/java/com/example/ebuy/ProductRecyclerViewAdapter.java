package com.example.ebuy;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ebuy.Model.Product;

import java.util.ArrayList;
import java.util.Locale;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductRecyclerViewHolder> {

    private ArrayList<Product> mProducts;
    private OnProductListener mListener;

    public ProductRecyclerViewAdapter( ArrayList<Product> mProducts, OnProductListener listener) {

        this.mProducts = mProducts;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_item, parent, false);
        return new ProductRecyclerViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewHolder holder, int position) {
        holder.Description.setText(mProducts.get(position).getProductDescription());
        holder.Brand.setText(mProducts.get(position).getBrand());
        holder.ProductCounter.setText(mProducts.get(position).get);
        holder.Price.setText(String.format(Locale.GERMANY, "%.2f", mProducts.get(position).getPrice()) + "€");
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public static class ProductRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Description;
        TextView Price;
        TextView Brand;
        TextView ProductCounter;
        ConstraintLayout LayoutForItem;
        LinearLayout DetailsLayout;
        OnProductListener listener;

        public ProductRecyclerViewHolder(@NonNull View itemView, OnProductListener listener) {
            super(itemView);
            LayoutForItem = itemView.findViewById(R.id.item_layout);
            Description = itemView.findViewById(R.id.description_textview);
            Brand = itemView.findViewById(R.id.brand_textview);
            Price = itemView.findViewById(R.id.price_textview);
            ProductCounter = itemView.findViewById(R.id.product_counter);

            DetailsLayout = itemView.findViewById(R.id.detail_layout);
            this.listener = listener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onProductClick(getAdapterPosition());

            DetailsLayout.setVisibility(View.VISIBLE);
        }
    }

    public interface OnProductListener {
        void onProductClick(int position);
    }
}
