package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.CartAdapter;
import com.example.tokostore020302.adapters.OrderDetailAdapter;
import com.example.tokostore020302.fragments.OrderFragment;
import com.example.tokostore020302.models.Cart;
import com.example.tokostore020302.models.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class OrderDetailActivity extends AppCompatActivity {


    private final Gson gson = new Gson();
    TextView totalPriceTxt, customerAddress, orderCustomerNname;
    CartAdapter cartAdapter;
    RecyclerView rv_order_detail;

    private ProductDatabase db;
    ArrayList<Cart> cartArrayList;


    Button orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        rv_order_detail = findViewById(R.id.rv_order_detail);
        totalPriceTxt = findViewById(R.id.order_total_price);
        orderBtn = findViewById(R.id.order_btn);
        customerAddress = findViewById(R.id.order_customer_address);
        orderCustomerNname = findViewById(R.id.order_customer_name);

        db = new ProductDatabase(this);

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

        //Tính tổng giá tiền trong đơn hàng
        double totalPrice = db.calculateTotalPrice();
        String formattedPrice = String.format(Locale.US, "%,d $", (int) totalPrice);
        totalPriceTxt.setText(formattedPrice);


        SharedPreferences sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        String userPreferences = sharedPreferences.getString(SharedUtils.SHARE_KEY_USER, null);
        User user = gson.fromJson(userPreferences, User.class);

        if (user != null) {
            customerAddress.setText(user.getAddress());
            String fullName = user.getFirstname() + " " + user.getLastname();
            orderCustomerNname.setText(fullName);
        }


    }


}