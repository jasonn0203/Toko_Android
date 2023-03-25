package com.example.tokostore020302.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tokostore020302.R;
import com.example.tokostore020302.fragments.AccountFragment;
import com.example.tokostore020302.fragments.HomePageFragment;
import com.example.tokostore020302.fragments.OrderFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView btMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Thay đổi màu sắc của Action Bar
        getSupportActionBar().setBackgroundDrawable
                (new ColorDrawable(getResources().getColor(R.color.primary)));
        //Hiển thị nút back action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);







        btMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        btMenu.setOnItemSelectedListener(bottomNavItemClicked());
    }

    @NonNull
    private NavigationBarView.OnItemSelectedListener bottomNavItemClicked() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Dựa vào ID trên từng Item trong bottom nav để load Fragment tương ứng
                switch (item.getItemId()) {
                    case R.id.homePanel: //TRANG HOME
                        // Thay đổi tiêu đề của Action Bar
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
                        LoadFragment(new HomePageFragment());
                        break;


                    case R.id.ordersPanel: //TRANG ORDER
                        // Thay đổi tiêu đề của Action Bar
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Orders");
                        LoadFragment(new OrderFragment());
                        break;

                    case R.id.accountPanel: //TRANG ACCOUNT
                        // Thay đổi tiêu đề của Action Bar
                        Objects.requireNonNull(getSupportActionBar()).setTitle("Account");
                        LoadFragment(new AccountFragment());
                        break;
                }
                return false;
            }
        };
    }


    //LOAD FRAGMENT vào VIEW
    void LoadFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        //Replace fragment với fragment khác được chọn
        //Đối số thứ 1 là container chứa Fragment, đối số thứ 2 là fragment truyền vào
        fragmentTransaction.replace(R.id.fragment_container, newFragment);
        //Đưa fragment hiện tại vào stack ( cho việc bấm nút Back trên thiết bị )
        fragmentTransaction.addToBackStack(null);
        //Thực hiện việc load và hiển thị Fragment ( Bắt buộc phải có dòng này )
        fragmentTransaction.commit();
    }
}