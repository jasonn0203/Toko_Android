package com.example.tokostore020302.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.User;

import java.util.Objects;

public class EditAccountActivity extends AppCompatActivity {

    EditText edtFirstname, edtLastname, edtAddress;
    Button updateInfoBtn;

    ProductDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);


        db = new ProductDatabase(this);


        Objects.requireNonNull(getSupportActionBar()).hide();
        edtFirstname = findViewById(R.id.editFirstNameField);
        edtLastname = findViewById(R.id.editLastNameField);
        edtAddress = findViewById(R.id.editAddressField);
        updateInfoBtn = findViewById(R.id.editUpdateBtn);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstname");
        String lastName = intent.getStringExtra("lastname");
        String address = intent.getStringExtra("address");

        edtFirstname.setText(firstName);
        edtLastname.setText(lastName);
        edtAddress.setText(address);


        updateInfoBtn.setOnClickListener(updateUserInfo());
    }

    @NonNull
    private View.OnClickListener updateUserInfo() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();

                user.setFirstname(edtFirstname.getText().toString());
                user.setLastname(edtLastname.getText().toString());
                user.setAddress(edtAddress.getText().toString());

                db.updateUserInfo(user, getApplicationContext());

                Toast.makeText(EditAccountActivity.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        };
    }
}