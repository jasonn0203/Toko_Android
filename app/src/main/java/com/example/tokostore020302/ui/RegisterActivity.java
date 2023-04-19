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

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.User;
import com.example.tokostore020302.models.Users;
import com.google.gson.Gson;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextView toLogin;
    EditText firstNameField, lastNameField, addressField, emailField, pwField;
    ImageView forwardBtn;
    private ProductDatabase database;

    private SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*//Khai báo Shared
        sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();*/

        database = new ProductDatabase(this);


        //Ẩn action bar mặc định
        Objects.requireNonNull(getSupportActionBar()).hide();

        toLogin = (TextView) findViewById(R.id.toLoginBtn);
        forwardBtn = (ImageView) findViewById(R.id.forwardBtn);
        firstNameField = (EditText) findViewById(R.id.firstNameField);
        lastNameField = (EditText) findViewById(R.id.lastNameField);
        emailField = (EditText) findViewById(R.id.emailField);
        addressField = (EditText) findViewById(R.id.addressField);
        pwField = (EditText) findViewById(R.id.passwordField);


        toLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        //Click nút submit
        //forwardBtn.setOnClickListener(checkValidate());

        forwardBtn.setOnClickListener(validateRegister());


    }

    @NonNull
    private View.OnClickListener validateRegister() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = firstNameField.getText().toString().trim();
                String lastName = lastNameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String address = addressField.getText().toString();
                String pw = pwField.getText().toString().trim();

                boolean isValidRegister = checkValidateRegister(firstName, lastName, email, pw);

                if (isValidRegister) {
                    //Them user vao db

                    User user = new User(0, firstName, lastName, email, address, pw);
                    if (database.registerUser(user)) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);




                        //Lưu user đã tạo vào SharedPreferences
                        user.setFirstname(firstName);
                        user.setLastname(lastName);


                        /*//----Convert User từ Object --> String với format là JSON để lưu vào Shared----

                        //Ép sang Json vì newUser là object và lưu vào biến userString
                        String userString = gson.toJson(user);

                        //editor sử dụng để thực hiện các thao tác chỉnh sửa dữ liệu trên SharedPreferences
                        editor.putString(SharedUtils.SHARE_KEY_USER, userString);//Lưu chuỗi JSON
                        editor.commit();//Lưu các thay đổi

*/


                        // Lưu thông tin người dùng vào SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstName", firstName);
                        editor.putString("lastName", lastName);
                        editor.putString("address", address);
                        editor.putString("email", email);
                        editor.putString("password", pw);
                        editor.apply();

                        startActivity(intent);



                    } else
                        Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

//    @NonNull
//    private View.OnClickListener checkValidate() {
//        return view -> {
//            String firstName = firstNameField.getText().toString().trim();
//            String lastName = lastNameField.getText().toString().trim();
//            String email = emailField.getText().toString().trim();
//            String pw = pwField.getText().toString().trim();
//            boolean isValid = validateRegister(firstName, lastName, email, pw);
//
//            //Điều kiện trùng khớp với yêu cầu đăng ký tài khoản
//            if (isValid) {
//
//                //Tạo 1 User để lưu vào shared pre
//                Users newUser = new Users();
//                newUser.setFirstName(firstName);
//                newUser.setLastName(lastName);
//                newUser.setEmail(email);
//                newUser.setPassword(pw);
//
//                //----Convert User từ Object --> String với format là JSON để lưu vào Shared----
//
//                //Ép sang Json vì newUser là object và lưu vào biến userString
//                String userString = gson.toJson(newUser);
//
//                //editor sử dụng để thực hiện các thao tác chỉnh sửa dữ liệu trên SharedPreferences
//                editor.putString(SharedUtils.SHARE_KEY_USER, userString);//Lưu chuỗi JSON
//                editor.commit();//Lưu các thay đổi
//
//
//                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(RegisterActivity.this,
//                        LoginActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//
//        };
//    }

    private boolean checkValidateRegister(String firstName, String lastName, String email, String pw) {
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
            pwField.setError("Password phải nhiều hơn 4 ký tự");
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