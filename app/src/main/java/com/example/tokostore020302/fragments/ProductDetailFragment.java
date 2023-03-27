package com.example.tokostore020302.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tokostore020302.R;
import com.example.tokostore020302.ui.SharedUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

//
public class ProductDetailFragment extends Fragment {
    TextView txtProdName, txtProdPrice, txtProdDesc;
    Button btnAddToCart;
    ImageView prodImg;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        String desc = bundle.getString("desc");
        double price = bundle.getDouble("price");
        String image = bundle.getString("image");



        txtProdName = (TextView) view.findViewById(R.id.productDetailName);
        txtProdPrice = view.findViewById(R.id.productDetailPrice);
        txtProdDesc = view.findViewById(R.id.productDetailDesc);
        prodImg = view.findViewById(R.id.productDetailImage);

        txtProdName.setText(name);
        txtProdPrice.setText(price + " $ ");
        txtProdDesc.setText(desc);

        prodImg.setImageBitmap(SharedUtils.convertToBitmapFromAssets(requireActivity(), image));


        //Add to cart
        btnAddToCart = (Button) view.findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}