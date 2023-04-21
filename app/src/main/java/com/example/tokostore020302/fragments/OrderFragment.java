package com.example.tokostore020302.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.CartAdapter;
import com.example.tokostore020302.models.Cart;
import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.ui.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements CartAdapter.OnItemClickListener {

    RecyclerView rvOrder;
    TextView announce_order_text;
    Button order_detail_btn;
    CartAdapter cartAdapter;
    ProductDatabase db;

    ArrayList<Cart> cartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        order_detail_btn = view.findViewById(R.id.order_detail_btn);
        announce_order_text = view.findViewById(R.id.order_text_announce);
        rvOrder = view.findViewById(R.id.rv_order);
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));


        db = new ProductDatabase(getContext());

        //Load danh sách giỏ hàng
        cartList = getCartContents();
        cartAdapter = new CartAdapter(cartList, getContext());
        cartAdapter.setListener(this);
        rvOrder.setAdapter(cartAdapter);
        cartAdapter.setIsOnAnotherActivity(false);

        displayAnnounceText();

        //Bấm nút chi tiết đơn hàng
        order_detail_btn.setOnClickListener(navigateToOrderDetail());


        return view;

    }

    @NonNull
    private View.OnClickListener navigateToOrderDetail() {
        return view -> {
            Intent intent = new Intent(getActivity(), OrderDetailActivity.class);

            intent.putParcelableArrayListExtra("cart_list", cartList);

            startActivity(intent);


        };
    }

    //Hien thi danh sach sp trong gio hang
    public ArrayList<Cart> getCartContents() {

        ArrayList<Cart> cartList = db.getCartContents();
        db.close();

        //Neu gio hang rong thi tra ve null
        if (cartList.size() == 0) {
            return null;
        }

        //Tra ve danh sach gio hang
        return cartList;

    }


    @Override
    public void onButtonDeleteClicked(Cart cartItem, int position) {
        db.removeFromCart(cartItem.getProduct());
        db.close();
        if (position >= 0 && position < cartList.size()) {
            cartList.remove(position);
            cartAdapter.notifyItemRemoved(position);
        }

        displayAnnounceText();

        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQuantityUp(Cart cartItem, int position) {
        int quantity = cartItem.getQuantity() + 1;
        if (quantity <= 10) { // kiểm tra số lượng
            cartItem.setQuantity(quantity);
            db.updateCartItem(cartItem); // cập nhật số lượng mới vào CSDL
            cartAdapter.notifyItemChanged(position); // cập nhật lại adapter
        }
    }

    @Override
    public void onQuantityDown(Cart cartItem, int position) {
        int quantity = cartItem.getQuantity();
        if (cartItem.getQuantity() <= 1)
            return;
        else {
            int quantityValue = quantity - 1;
            cartItem.setQuantity(quantityValue);
        }
        db.updateCartItem(cartItem); // cập nhật số lượng mới vào CSDL
        cartAdapter.notifyItemChanged(position); // cập nhật lại adapter
    }

    void displayAnnounceText() {
        if (cartList != null && cartList.size() > 0) {
            announce_order_text.setVisibility(View.GONE);
            order_detail_btn.setVisibility(View.VISIBLE);
        } else {
            announce_order_text.setVisibility(View.VISIBLE);
            order_detail_btn.setVisibility(View.GONE);

        }
    }
}