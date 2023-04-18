package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.CartAdapter;
import com.example.tokostore020302.adapters.OrderDetailAdapter;
import com.example.tokostore020302.models.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDetailActivity extends AppCompatActivity {

    OrderDetailAdapter orderDetailAdapter;
    CartAdapter cartAdapter;
    RecyclerView rv_order_detail;

    ArrayList<Cart> cartArrayList;

    Button orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        rv_order_detail = findViewById(R.id.rv_order_detail);
        orderBtn = findViewById(R.id.order_btn);

        Objects.requireNonNull(getSupportActionBar()).hide();

        //Nhận dữ liệu
        Intent intent = getIntent();
        cartArrayList = intent.getParcelableArrayListExtra("cart_list");


        //Gán adapter
        cartAdapter = new CartAdapter(cartArrayList, this);
        rv_order_detail.setAdapter(cartAdapter);
        rv_order_detail.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter.setIsOnAnotherActivity(true);


        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailActivity.this, ConfirmOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}