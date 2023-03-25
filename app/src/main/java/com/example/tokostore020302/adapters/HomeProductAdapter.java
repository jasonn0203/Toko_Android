package com.example.tokostore020302.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.ui.SharedUtils;

import java.util.List;


//ADAPTER CHO TRANG HOME
public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private List<Product> products;
    Context context;


//    public HomeProductAdapter(List<Product> products, OnItemClickListener listener) {
//        this.products = products;
//        this.listener = listener;
//    }
//
//    public HomeProductAdapter(List<Product> products, Context context) {
//        this.products = products;
//        this.context = context;
//    }


    public HomeProductAdapter(List<Product> products, Context context, OnItemClickListener listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }
    //-------------------------------------------------------------------

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_gv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product product = products.get(position);

        holder.productImageView.setImageBitmap(SharedUtils.convertToBitmapFromAssets(context, product.getImage()));

        holder.productNameTxt.setText(product.getName());
        holder.productPriceTxt.setText(String.valueOf(product.getPrice()));

        //Click vào sản phẩm
        holder.itemView.setOnClickListener(view -> listener.onItemClick(product));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
    //-------------------------------------------------------------------

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImageView;
        TextView productNameTxt, productPriceTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.product_image);
            productNameTxt = itemView.findViewById(R.id.product_name);
            productPriceTxt = itemView.findViewById(R.id.product_price);


        }
    }


    public interface OnItemClickListener {
        void onItemClick(Product product);

    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
