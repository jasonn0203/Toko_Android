package com.example.tokostore020302.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.CartAdapter;
import com.example.tokostore020302.models.Cart;

import java.util.List;

public class OrderFragment extends Fragment implements CartAdapter.OnItemClickListener {

    RecyclerView rvOrder;
    TextView announce_order_text;
    CartAdapter cartAdapter;
    ProductDatabase db;

    List<Cart> cartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);


        announce_order_text = view.findViewById(R.id.order_text_announce);
        rvOrder = view.findViewById(R.id.rv_order);
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));


        db = new ProductDatabase(getContext());
        cartList = getCartContents();
        cartAdapter = new CartAdapter(cartList, getContext());
        cartAdapter.setListener(this);
        rvOrder.setAdapter(cartAdapter);

        displayAnnounceText();


        return view;

    }

    //Hien thi danh sach sp trong gio hang
    public List<Cart> getCartContents() {

        List<Cart> cartList = db.getCartContents();
        db.close();


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

    }

    @Override
    public void onQuantityDown(Cart cartItem, int position) {

    }

    void displayAnnounceText() {
        if (cartList.size() > 0)
            announce_order_text.setVisibility(View.GONE);
        else
            announce_order_text.setVisibility(View.VISIBLE);

    }
}