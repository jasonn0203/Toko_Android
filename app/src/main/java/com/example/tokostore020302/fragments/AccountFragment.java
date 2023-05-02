package com.example.tokostore020302.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.GoogleUser;
import com.example.tokostore020302.models.User;
import com.example.tokostore020302.models.Users;
import com.example.tokostore020302.ui.AboutActivity;
import com.example.tokostore020302.ui.EditAccountActivity;
import com.example.tokostore020302.ui.LoginActivity;
import com.example.tokostore020302.ui.RegisterActivity;
import com.example.tokostore020302.ui.SharedUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Objects;


public class AccountFragment extends Fragment {

    ImageView accountImg;
    ProductDatabase db;
    Context context;
    TextView txtName, txtEmail, accountInfo, aboutStore;
    private SharedPreferences.Editor editor;
    SharedPreferences getShared;

    User user;

    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();


    GoogleUser googleUser = new GoogleUser();
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GoogleUser").child(firebaseUser.getUid());


    //------------------------ ĐĂNG XUẤT ----------------------------
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        getShared = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.log_out_icon, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOutIcon) {

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MY_PREFERENCES", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (sharedPreferences.getBoolean("isGoogleSignIn", false)) {
                // Đăng xuất theo kiểu của Google

                GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
            } else {
                // Đăng xuất theo kiểu của SQLite
                db.logOut(user, requireContext());
                editor.clear();
                editor.apply();
            }
            Intent intent = new Intent(getContext(), LoginActivity.class);
            Toast.makeText(context, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            getActivity().finish();



           /* if (isGoogleSignIn) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();

            } else {
                db.logOut(user, getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                Toast.makeText(context, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                getActivity().finish();
            }*/


            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    // ---------------------------------------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        txtName = (TextView) view.findViewById(R.id.accountDisplayName);
        accountInfo = view.findViewById(R.id.txtAccountInfo);
        db = new ProductDatabase(getContext());


        user = new User();
        String nameInfo = user.getFirstname() + " " + user.getLastname();
        txtName.setText(nameInfo);
        accountInfo.setOnClickListener(editAccountInfo());


        aboutStore = view.findViewById(R.id.txtAboutStore);
        aboutStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });





        /*Khi đăng nhập bằng Google*/
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                googleUser = snapshot.getValue(GoogleUser.class);

                //Update Info
                updateInfoGoogleAccount(getContext(), Objects.requireNonNull(googleUser));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void updateInfoGoogleAccount(Context context, GoogleUser googleUser) {
        txtName.setText(googleUser.getName());
      /*  if (googleUser.getProfile() != null) {
            Glide.with(context).load(googleUser.getProfile()).into(accountImg);
        }else {

             accountImg.setImageResource(R.drawable.user_avatar);

        }*/
    }


    @NonNull
    private View.OnClickListener editAccountInfo() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, MODE_PRIVATE);
                String userPreferences = sharedPreferences.getString(SharedUtils.SHARE_KEY_USER, null);
                User user = gson.fromJson(userPreferences, User.class);
                Intent intent = new Intent(getActivity(), EditAccountActivity.class);
                intent.putExtra("firstname", user.getFirstname());
                intent.putExtra("lastname", user.getLastname());
                intent.putExtra("address", user.getAddress());

                startActivity(intent);
            }
        };
    }


    private void updateUserName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, MODE_PRIVATE);
        String userPreferences = sharedPreferences.getString(SharedUtils.SHARE_KEY_USER, null);
        User user = gson.fromJson(userPreferences, User.class);

        if (user != null) {
            String nameInfo = user.getFirstname() + " " + user.getLastname();
            txtName.setText(nameInfo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserName();

    }
}