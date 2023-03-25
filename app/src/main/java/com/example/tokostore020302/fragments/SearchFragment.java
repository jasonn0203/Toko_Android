package com.example.tokostore020302.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Products;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    ArrayList<Products> productsArrayList;
    //ProductAdapter productAdapter;
    ImageButton btnSearch;
    ImageView img_no_product;
    EditText searchInput;
    RecyclerView productGrid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        productGrid = view.findViewById(R.id.product_grid_search);
        btnSearch = (ImageButton) view.findViewById(R.id.btnSearch);
        searchInput = (EditText) view.findViewById(R.id.searchInput);
        img_no_product = (ImageView) view.findViewById(R.id.img_no_product);
        //Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null)
            productsArrayList = (ArrayList<Products>) bundle.getSerializable("productsList");


        //productAdapter = new ProductAdapter(getContext(), R.layout.product_gv_item, new ArrayList<>());
        //productGrid.setAdapter(productAdapter);
        //Span count : số cột hiển thị ra View
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        productGrid.setLayoutManager(layoutManager);


        //btnSearch.setOnClickListener(querySearch());

        return view;
    }

//    @NonNull
//    private View.OnClickListener querySearch() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Lấy dữ liệu người dùng nhập từ Input
//                String query = searchInput.getText().toString().trim().toLowerCase();
//
//                //Tạo danh sách lưu trữ kết quả tìm kiếm
//                ArrayList<Products> searchResults = new ArrayList<>();
//
//                if (productsArrayList != null) {
//                    //Lặp qua danh sách sản phẩm và lấy ra SP trùng khớp tìm kiếm
//                    for (Products product : productsArrayList) {
//                        //Lấy SP dựa theo tên SP hoặc lấy theo mô tả
//                        if (product.getProdName().toLowerCase().contains(query) || product.getProdDesc().toLowerCase().contains(query)) {
//                            searchResults.add(product);
//                        }
//                    }
////                    productAdapter.querySearch(searchResults);
//
//                    Log.d("Search Results", "Found " + searchResults.size() + " products");
//                    //Trường hợp không có Kết Quả trùng khớp
//                    if (searchResults.size() == 0) {
//                        Toast.makeText(getContext(), "Không có sản phẩm bạn cần tìm !", Toast.LENGTH_SHORT).show();
//                    } else {
//                        img_no_product.setImageResource(0);
//                        //Trường hợp có kết quả
//                        //Tạo adapter cho Recycle view
//                        productAdapter = new ProductAdapter(getContext(), R.layout.product_gv_item, searchResults);
//                        productGrid.setAdapter(productAdapter);
//                    }
//                } else {
//                    img_no_product.setImageResource(R.drawable.no_product_found);
//                }
//            }
//
//        };
    // }
}