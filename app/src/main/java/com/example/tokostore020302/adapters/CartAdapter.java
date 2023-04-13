package com.example.tokostore020302.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Cart;
import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.ui.SharedUtils;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    private List<Cart> cartList;

    private OnItemClickListener listener;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_order_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cartItem = cartList.get(position);
        Product product = cartItem.getProduct();
        holder.productName.setText(cartItem.getProduct().getName());
        holder.productPrice.setText(String.valueOf(cartItem.getProduct().getPrice()));
        holder.productQuantity.setText(String.valueOf(cartItem.getQuantity()));


        // Kiểm tra số lượng sản phẩm để ẩn/hiện nút tăng số lượng
        if (cartItem.getQuantity() >= 10) {
            holder.quantityUp.setVisibility(View.GONE);
            Toast.makeText(context, "Chỉ được thêm tối đa 10 sản phẩm !", Toast.LENGTH_SHORT).show();
        } else {
            holder.quantityUp.setVisibility(View.VISIBLE);
        }
        holder.quantityUp.setOnClickListener(view -> listener.onQuantityUp(cartItem,position));
        holder.quantityDown.setOnClickListener(view -> listener.onQuantityDown(cartItem,position));

        holder.productImage.setImageBitmap(SharedUtils.convertToBitmapFromAssets(context, product.getImage()));

        holder.cartDeleteBtn.setOnClickListener(view -> listener.onButtonDeleteClicked(cartItem, position));




    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, quantityUp, quantityDown;
        TextView productName;
        TextView productPrice;
        TextView productQuantity;

        Button cartDeleteBtn;

        public CartViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.rv_order_image);
            productName = itemView.findViewById(R.id.order_product_name);
            productPrice = itemView.findViewById(R.id.order_product_price);
            productQuantity = itemView.findViewById(R.id.cart_quantity);
            cartDeleteBtn = itemView.findViewById(R.id.cart_delete_btn);
            quantityUp = itemView.findViewById(R.id.quantity_up);
            quantityDown = itemView.findViewById(R.id.quantity_down);
        }
    }


    public interface OnItemClickListener {

        void onButtonDeleteClicked(Cart cartItem, int position);
        void onQuantityUp(Cart cartItem,int position);
        void onQuantityDown(Cart cartItem,int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


