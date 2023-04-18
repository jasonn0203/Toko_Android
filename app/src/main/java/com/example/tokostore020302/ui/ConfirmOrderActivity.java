package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tokostore020302.R;
import com.example.tokostore020302.fragments.HomePageFragment;
import com.example.tokostore020302.fragments.OrderFragment;

import java.util.Objects;

public class ConfirmOrderActivity extends AppCompatActivity {

    Button backToHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        Objects.requireNonNull(getSupportActionBar()).hide();
        backToHomePage = findViewById(R.id.back_homepage_btn);

        backToHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ConfirmOrderActivity.this,HomeActivity.class);
               startActivity(intent);
            }
        });
    }
}