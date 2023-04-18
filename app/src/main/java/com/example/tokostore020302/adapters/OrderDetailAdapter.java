package com.example.tokostore020302.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Cart;
import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.ui.SharedUtils;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {


    Context context;
    private ArrayList<Cart> cartList;

    public OrderDetailAdapter(ArrayList<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_item, parent, false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Cart cartItem = cartList.get(position);
        Product product = cartItem.getProduct();
        if (product != null) {
            holder.productName.setText(product.getName());
            holder.productPrice.setText(String.valueOf(product.getPrice()));
            holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));
            holder.productImage.setImageBitmap(SharedUtils.convertToBitmapFromAssets(context, product.getImage()));
        }



    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productQuantity;
        TextView productPrice;
        Button order_detail_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.rv_order_image_detail);
            productName = itemView.findViewById(R.id.order_product_name_detail);
            productPrice = itemView.findViewById(R.id.order_product_price_detail);
            productQuantity = itemView.findViewById(R.id.cart_quantity_detail);
            order_detail_btn = itemView.findViewById(R.id.order_detail_btn);
        }

    }
}
