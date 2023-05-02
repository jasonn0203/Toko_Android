package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tokostore020302.R;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        Objects.requireNonNull(getSupportActionBar()).hide();


    }
}