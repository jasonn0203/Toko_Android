package com.example.tokostore020302.fragments;

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

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.User;
import com.example.tokostore020302.models.Users;
import com.example.tokostore020302.ui.EditAccountActivity;
import com.example.tokostore020302.ui.LoginActivity;
import com.example.tokostore020302.ui.RegisterActivity;
import com.example.tokostore020302.ui.SharedUtils;
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
           /* //Xoá bộ nhớ cache của ứng dụng
            WebView webView = new WebView(getContext());
            webView.clearCache(true);

            //Xoá dữ liệu người dùng mới nhất trong SharedPreferences trước khi đăng xuất
            onAttach(getContext());
            editor = getShared.edit();
            editor.remove(SharedUtils.SHARE_KEY_USER); // xóa khóa mới
            editor.apply();

            getActivity().finish();

            Intent intent = new Intent(getContext(), LoginActivity.class);
            Toast.makeText(context, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            startActivity(intent);*/

            db.logOut(user, getContext());
            Intent intent = new Intent(getContext(), LoginActivity.class);
            Toast.makeText(context, "Đăng xuất thành công!", Toast.LENGTH_SHORT).show();
            startActivity(intent);

            getActivity().finish();

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
        accountInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
                String userPreferences = sharedPreferences.getString(SharedUtils.SHARE_KEY_USER, null);
                User user = gson.fromJson(userPreferences, User.class);
                Intent intent = new Intent(getActivity(), EditAccountActivity.class);
                intent.putExtra("firstname", user.getFirstname());
                intent.putExtra("lastname", user.getLastname());
                intent.putExtra("address", user.getAddress());

                startActivity(intent);
            }
        });


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