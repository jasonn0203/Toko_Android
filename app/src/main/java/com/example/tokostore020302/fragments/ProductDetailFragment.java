package com.example.tokostore020302.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.models.Product;
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
        int id = bundle.getInt("id");
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


                // Tạo đối tượng Product mới
                Product product = new Product(id, name, price, image);
                // Thêm sản phẩm vào giỏ hàng
                ProductDatabase databaseHelper = new ProductDatabase(getContext());
                databaseHelper.addToCart(product, 1); // Giả sử số lượng sản phẩm được thêm vào là 1
                databaseHelper.close();

                // Chuyển sang OrderFragment
                Fragment orderFragment = new OrderFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, orderFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                Toast.makeText(getContext(), "Thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();


            }
        });

        return view;
    }
}