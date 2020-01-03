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

    private ArrayList<Product> mProducts;
    private Context context;

    public ProductRecyclerViewAdapter(Context context, ArrayList<Product> mProducts){
        this.context=context;
        this.mProducts=mProducts;
    }

    @NonNull
    @Override
    public ProductRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recyclerview_item, parent, false);
        return new ProductRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRecyclerViewHolder holder, int position) {
        holder.Description.setText(mProducts.get(position).getProductDescription());
        holder.Brand.setText(mProducts.get(position).getBrand());
        holder.Price.setText(String.format(Locale.GERMANY,"%.2f", mProducts.get(position).getPrice())+"€" );
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
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
