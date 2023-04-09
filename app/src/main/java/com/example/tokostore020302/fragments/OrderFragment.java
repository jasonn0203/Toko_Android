package com.example.tokostore020302.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.CartAdapter;
import com.example.tokostore020302.models.Cart;

import java.util.List;

public class OrderFragment extends Fragment implements CartAdapter.OnItemClickListener {

    RecyclerView rvOrder;
    CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        rvOrder = view.findViewById(R.id.rv_order);
        rvOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Cart> cartList = getCartContents();
        cartAdapter = new CartAdapter(cartList, getContext());
        cartAdapter.setListener(this);
        rvOrder.setAdapter(cartAdapter);
        return view;


    }

    public List<Cart> getCartContents() {
        ProductDatabase db = new ProductDatabase(getContext());
        List<Cart> cartList = db.getCartContents();
        db.close();
        return cartList;
    }


    @Override
    public void onButtonDeleteClicked(Cart cartItem, int position) {

    }

    @Override
    public void onQuantityUp(Cart cartItem, int position) {

    }

    @Override
    public void onQuantityDown(Cart cartItem, int position) {

    }
}