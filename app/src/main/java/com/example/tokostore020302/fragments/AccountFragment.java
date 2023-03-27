package com.example.tokostore020302.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Users;
import com.example.tokostore020302.ui.SharedUtils;
import com.google.gson.Gson;


public class AccountFragment extends Fragment {

    ImageView accountImg;
    TextView txtName, txtEmail;

    //Chuyển đổi java thành XML
    private final Gson gson = new Gson();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        txtName = (TextView) view.findViewById(R.id.accountDisplayName);

        /*SharedPreferences getShared = getActivity().getSharedPreferences(SharedUtils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        //Lấy ra từ Shared
        String userGetShared = getShared.getString(SharedUtils.SHARE_KEY_USER, null);

        //Lấy ra giá trị shared từ model User và key share
        Users user = gson.fromJson(userGetShared, Users.class);

        //Trường hợp không có User
        if (user == null)
            txtName.setText("Annoynimus");
        else {
            String nameInfo = user.getFirstName() + " " + user.getLastName();
            txtName.setText(nameInfo);

        }*/

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String firstname = intent.getStringExtra("firstname");
            String lastname = intent.getStringExtra("lastname");
            String nameInfo = firstname + " " + lastname;
            txtName.setText(nameInfo);
        } else
            txtName.setText("Non-login user");


        return view;
    }
}