package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.User;
import com.example.tokostore020302.models.Users;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView toRegister;

    ImageView forwardBtn;

    EditText emailField, pwField;
    private ProductDatabase database;

    SharedPreferences sharedPreferences;

    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Khai báo Shared (Không cần editor vì chỉ cần lấy Dữ Liệu)
        sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);

        database = new ProductDatabase(this);

        Objects.requireNonNull(getSupportActionBar()).hide();

        toRegister = (TextView) findViewById(R.id.toRegisterBtn);
        forwardBtn = (ImageView) findViewById((R.id.forwardBtn));
        emailField = (EditText) findViewById(R.id.emailField);
        pwField = (EditText) findViewById(R.id.passwordField);

        //Chuyển qua trang Đăng ký
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        //Login btn
        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString().trim();
                String password = pwField.getText().toString().trim();

                //Case admin
                if (email.equals("admin") && password.equals("admin")) {
                    //Chuyển tới sang admin
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }

                checkEmailLogin(email);
                checkPwLogin(password);

                ArrayList<User> userList = database.loginUser(email, password);

                if (userList.size() != 0) {
                    User user = userList.get(0);
                    Toast.makeText(LoginActivity.this, "Tài khoản hợp lệ!", Toast.LENGTH_SHORT).show();


                    Intent intent = getIntent();
                    String firstname = intent.getStringExtra("firstname");
                    String lastname = intent.getStringExtra("lastname");

                    Intent HomeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("firstname", firstname);
                    bundle.putString("lastname", lastname);
                    HomeIntent.putExtras(bundle);
                    startActivity(HomeIntent);
                } else
                    Toast.makeText(LoginActivity.this, "Tài khoản không hợp lệ!", Toast.LENGTH_SHORT).show();


            }
        });
    }

/*    private void checkValidLogin() {
        String email = emailField.getText().toString().trim();
        String password = pwField.getText().toString().trim();

        //Case admin
        if (email.equals("admin") && password.equals("admin")) {
            //Chuyển tới sang admin
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }


        //Lấy user từ Shared
        String userPreferences = sharedPreferences.getString(SharedUtils.SHARE_KEY_USER, null);
        //
        Users user = gson.fromJson(userPreferences, Users.class);
        //Trường hợp User không tồn tại trong Shared
        if (user == null) {
            Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValidLogin = (email.equals(user.getEmail()) && password.equals(user.getPassword()));

        if (checkEmailLogin(email) && checkPwLogin(password) && isValidLogin) {
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            //Truyền bundle qua Intent
            Bundle bundle = new Bundle();
            //Truyền qua putSerializable vì "user" là object
            bundle.putSerializable(SharedUtils.SHARE_KEY_USER, user);

            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email hoặc password không tồn tại!", Toast.LENGTH_SHORT).show();
        }


    }*/

    boolean checkEmailLogin(String email) {
        if (email.isEmpty()) {
            emailField.setError("Email khong duoc de trong");
            return false;
        }

        return true;
    }

    boolean checkPwLogin(String password) {

        if (password.isEmpty()) {
            pwField.setError("Password khong duoc de trong");
            return false;
        }
        return true;
    }


}

