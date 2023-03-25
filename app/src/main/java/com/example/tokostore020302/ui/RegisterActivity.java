package com.example.tokostore020302.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Users;
import com.google.gson.Gson;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextView toLogin;
    EditText firstNameField, lastNameField, emailField, pwField;
    ImageView forwardBtn;

    private SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Khai báo Shared
        sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //Ẩn action bar mặc định
        Objects.requireNonNull(getSupportActionBar()).hide();

        toLogin = (TextView) findViewById(R.id.toLoginBtn);
        forwardBtn = (ImageView) findViewById(R.id.forwardBtn);
        firstNameField = (EditText) findViewById(R.id.firstNameField);
        lastNameField = (EditText) findViewById(R.id.lastNameField);
        emailField = (EditText) findViewById(R.id.emailField);
        pwField = (EditText) findViewById(R.id.passwordField);


        toLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        //Click nút submit
        forwardBtn.setOnClickListener(checkValidate());


    }

    @NonNull
    private View.OnClickListener checkValidate() {
        return view -> {
            String firstName = firstNameField.getText().toString().trim();
            String lastName = lastNameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String pw = pwField.getText().toString().trim();
            boolean isValid = validateRegister(firstName, lastName, email, pw);

            //Điều kiện trùng khớp với yêu cầu đăng ký tài khoản
            if (isValid) {

                //Tạo 1 User để lưu vào shared pre
                Users newUser = new Users();
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setEmail(email);
                newUser.setPassword(pw);

                //----Convert User từ Object --> String với format là JSON để lưu vào Shared----

                //Ép sang Json vì newUser là object và lưu vào biến userString
                String userString = gson.toJson(newUser);

                //editor sử dụng để thực hiện các thao tác chỉnh sửa dữ liệu trên SharedPreferences
                editor.putString(SharedUtils.SHARE_KEY_USER, userString);//Lưu chuỗi JSON
                editor.commit();//Lưu các thay đổi


                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();

            }

        };
    }

    private boolean validateRegister(String firstName, String lastName, String email, String pw) {
        boolean isValid = true;

        if (firstName.isEmpty()) {
            firstNameField.setError("First name không được để trống");
            isValid = false;
        } else if (firstName.equals("admin")) {
            firstNameField.setError("First name không được đặt là 'admin'");
            isValid = false;
        } else {
            firstNameField.setError(null);
        }

        if (lastName.isEmpty()) {
            lastNameField.setError("Last name không được để trống");
            isValid = false;
        } else if (lastName.equals("admin")) {
            lastNameField.setError("Last name không được đặt là 'admin'");
            isValid = false;
        } else {
            lastNameField.setError(null);
        }

        if (email.isEmpty()) {
            emailField.setError("Email không được để trống!");
            isValid = false;
        } else if (!email.contains("@")) {
            emailField.setError("Email không hợp lệ");
            isValid = false;
        } else {
            emailField.setError(null);
        }

        if (pw.isEmpty()) {
            pwField.setError("Password không được để trống!");
            isValid = false;
        } else if (pw.length() < 4) {
            pwField.setError("Password phải nhiều hơn ký tự");
            isValid = false;
        } else if (pw.length() > 12) {
            pwField.setError("Password không được quá 12 ký tự");
            isValid = false;
        } else {
            pwField.setError(null);
        }

        return isValid;
    }

}