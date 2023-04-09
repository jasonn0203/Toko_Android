package com.example.tokostore020302.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.User;
import com.example.tokostore020302.models.Users;
import com.example.tokostore020302.ui.RegisterActivity;
import com.example.tokostore020302.ui.SharedUtils;
import com.google.gson.Gson;

import java.util.Objects;


public class AccountFragment extends Fragment {

    ImageView accountImg;
    ProductDatabase db;
    Context context;
    TextView txtName, txtEmail;
    private SharedPreferences.Editor editor;
    SharedPreferences getShared;

    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();


    //------------------------ ĐĂNG XUẤT ----------------------------
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        getShared = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
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
            //Khai báo Shared
            onAttach(getContext());
            editor = getShared.edit();

            //Clear dữ liệu người dùng trong Shared trước khi log out
            editor.remove(SharedUtils.SHARE_KEY_USER);
            editor.apply();


            Intent intent = new Intent(getContext(), RegisterActivity.class);
            Toast.makeText(context, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            startActivity(intent);

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



//        Bundle bundle = getActivity().getIntent().getExtras();
//        if (bundle != null) {
//            String username = bundle.getString("username");
//            String nameInfo = username;
//            txtName.setText(nameInfo);

        updateUserName();





        return view;
    }


    private void updateUserName() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
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