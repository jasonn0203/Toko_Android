package com.example.tokostore020302.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.ui.SharedUtils;

import java.util.List;


//ADAPTER CHO TRANG ADMIN

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Product> products;
    private OnItemClickListener listener;
    Context context;

    public RecyclerViewAdapter(List<Product> products, OnItemClickListener listener) {
        this.products = products;
        this.listener = listener;
    }

    public RecyclerViewAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }
    //-------------------------------------------------------------------
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_item_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.valueOf(product.getPrice()));
        holder.imageView.setImageBitmap(SharedUtils.convertToBitmapFromAssets(context, product.getImage()));

        holder.ivEdit.setOnClickListener(view -> listener.onItemEditClicked(product, position));
        holder.ivDelete.setOnClickListener(view -> listener.onItemDeleteClicked(product, position));

        holder.itemView.setOnClickListener(view -> listener.onItemClick(product));


    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    //-------------------------------------------------------------------

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextView;
        private TextView brandTextView;
        private TextView priceTextView;

        ImageView ivEdit, ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.adminProdImage);
            nameTextView = itemView.findViewById(R.id.adminProdName);
            priceTextView = itemView.findViewById(R.id.adminProdPrice);
            ivDelete = itemView.findViewById(R.id.deleteBtn);
            ivEdit = itemView.findViewById(R.id.editBtn);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);

        void onItemEditClicked(Product product, int position);

        void onItemDeleteClicked(Product product, int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
