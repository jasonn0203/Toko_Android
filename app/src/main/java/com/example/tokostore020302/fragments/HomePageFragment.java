package com.example.tokostore020302.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.HomeProductAdapter;
import com.example.tokostore020302.models.Product;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment implements HomeProductAdapter.OnItemClickListener {

    public HomePageFragment() {
        // Required empty public constructor
    }

    //-------------------------------------------------------------------

    //LinearLayout linearLayoutBrand;
    private Context context;
    ConstraintLayout searchLayout;

    HomeProductAdapter adapter;
    private List<Product> products = new ArrayList<>();
    RecyclerView rvHomeProduct;
    ProductDatabase db;


    ImageView noProdFoundImg, samsungBrand, appleBrand, miBrand, oppoBrand;
    Boolean isSearchLayoutVisible = false;//Biến lưu trạng thái visibility thanh search
    EditText searchInput;
    TextView txtProduct;


    //-------------------------------------------------------------------


    //CUSTOM ACTION BAR click icon để hiển thị ô search
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_display_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.searchIcon);
        searchItem.setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchIcon) {
            searchLayout = getView().findViewById(R.id.searchContainer);

            //Toggle hiển thị ô search
            if (!isSearchLayoutVisible) {
                searchLayout.setVisibility(View.VISIBLE);
                isSearchLayoutVisible = true;
            } else {
                searchLayout.setVisibility(View.GONE);
                isSearchLayoutVisible = false;

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        db = new ProductDatabase(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        //Ánh Xạ ---------------------------------------------------
        ImageView samsungBrand = view.findViewById(R.id.samsungCategory);
        ImageView appleBrand = view.findViewById(R.id.appleCategory);
        ImageView xiaomiBrand = view.findViewById(R.id.xiaomiCategory);
        ImageView oppoBrand = view.findViewById(R.id.oppoCategory);

        rvHomeProduct = (RecyclerView) view.findViewById(R.id.product_grid);

        //Adapter
        adapter = new HomeProductAdapter(products, context, this);
        rvHomeProduct.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        rvHomeProduct.setLayoutManager(layoutManager);

        //Load dữ liệu vào ds
        products.addAll(db.getAllProducts());
        adapter.notifyDataSetChanged();
        adapter.setListener(this);

        //Slider
        Banner((ViewFlipper) view.findViewById(R.id.simpleViewFlipper));


        return view;
    }

    private void Banner(ViewFlipper view) {
        //Slider quảng cáo
        ViewFlipper simpleViewFlipper = view;
        simpleViewFlipper.setAutoStart(true);
    }


    @Override
    public void onItemClick(Product product) {
        Toast.makeText(getContext(), product.getName(), Toast.LENGTH_SHORT).show();
    }
}
