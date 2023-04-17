package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.User;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText emailField, pwField, repeatPwField;
    ImageView resetPwBtn;

    ProductDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailField = findViewById(R.id.emailField);
        pwField = findViewById(R.id.passwordField);
        repeatPwField = findViewById(R.id.repeatPasswordField);
        resetPwBtn = findViewById(R.id.resetPwBtn);

        db = new ProductDatabase(this);

        Intent intent = getIntent();
        String emailLoginValue = intent.getStringExtra("emailValue");

        emailField.setText(emailLoginValue);

        resetPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Lấy dữ liệu ng dùng nhập
                String email = emailField.getText().toString().trim();
                String newPassword = pwField.getText().toString();
                String confirmNewPassword = repeatPwField.getText().toString();

                //Kiểm tra người dùng nhập mật khẩu có trùng khớp không
                if (confirmNewPassword.equals(newPassword)) {
                    //Tạo đối tượng user mới và gán giá trị đã cập nhật cho đối tượng đó
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(newPassword);

                    db.updatePassword(user);
                    //Thông báo cập nhật mk thành công
                    Toast.makeText(ForgotPasswordActivity.this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show();

                    //Quay về màn hình đăng nhập
                    finish();

                } else {
                    //MK không khớp , yêu cầu nhập lại
                    Toast.makeText(ForgotPasswordActivity.this, "Mật khẩu không khớp, xin hãy nhập lại!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}