package com.example.tokostore020302.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.example.tokostore020302.models.GoogleUser;
import com.example.tokostore020302.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextView toRegister, forgotPwBtn;

    ImageView forwardBtn, googleLogin;

    EditText emailField, pwField;
    private ProductDatabase database;

    private SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;


    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    GoogleSignInClient googleSignInClient;
    ProgressDialog progressDialog;
    int REQUEST_SIGNIN_CODE = 40;


    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Khai báo Shared
        sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        database = new ProductDatabase(this);

        Objects.requireNonNull(getSupportActionBar()).hide();

        toRegister = (TextView) findViewById(R.id.toRegisterBtn);
        forwardBtn = (ImageView) findViewById((R.id.forwardBtn));
        emailField = (EditText) findViewById(R.id.emailField);
        pwField = (EditText) findViewById(R.id.passwordField);
        forgotPwBtn = (TextView) findViewById(R.id.forgotPwBtn);


        //Chuyển qua trang reset Password
        forgotPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                intent.putExtra("emailValue", emailField.getText().toString());
                startActivity(intent);
            }
        });

        //Chuyển qua trang Đăng ký
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        //Login btn
        forwardBtn.setOnClickListener(validateLogin());


        //------------------Login bằng GOOGLE---------------------------
        googleLogin = findViewById(R.id.google_login);

        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Tạo tài khoản");
        progressDialog.setMessage("Chúng tôi đang đăng nhập vào tài khoản cho bạn !");


        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                handleGoogleLogin();
            }
        });


    }

    //Xử lý login bằng google firebase
    private void handleGoogleLogin() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, REQUEST_SIGNIN_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SIGNIN_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    GoogleUser googleUser = new GoogleUser();
                    googleUser.setUserID(user.getUid());
                    googleUser.setName(user.getDisplayName());
                    googleUser.setProfile(user.getPhotoUrl().toString());

                    firebaseDatabase.getReference("GoogleUser").child(user.getUid()).setValue(googleUser);

                    SharedPreferences sharedPreferences = getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isGoogleSignIn", true); // Nếu đăng nhập bằng Google
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);


                    startActivity(intent);
                    progressDialog.dismiss();


                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập bằng Google thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @NonNull
    private View.OnClickListener validateLogin() {
        return new View.OnClickListener() {
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


                    SharedPreferences sharedPreferences = getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(SharedUtils.SHARE_KEY_USER, gson.toJson(user));
                    editor.apply();


                    editor.putBoolean("isGoogleSignIn", false); // Nếu đăng nhập bằng Google
                    editor.apply();


                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                    startActivity(intent);

                    finish();


                } else
                    Toast.makeText(LoginActivity.this, "Tài khoản không hợp lệ!", Toast.LENGTH_SHORT).show();


            }
        };
    }


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

