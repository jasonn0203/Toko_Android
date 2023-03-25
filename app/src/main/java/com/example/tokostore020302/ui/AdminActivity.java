package com.example.tokostore020302.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tokostore020302.Database.ProductDatabase;
import com.example.tokostore020302.R;
import com.example.tokostore020302.adapters.RecyclerViewAdapter;
import com.example.tokostore020302.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener {

    RecyclerView rvProduct;
    List<Product> products = new ArrayList<>();
    RecyclerViewAdapter adapter;

    EditText edName, edBrand, edDesc, edPrice, edImage;

    ImageView imgProd;
    ProductDatabase db = new ProductDatabase(AdminActivity.this);

    Button addProductBtn, editProdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Ánh Xạ
        rvProduct = (RecyclerView) findViewById(R.id.rv_admin_product);
        edName = (EditText) findViewById(R.id.edTxtProdName);
        edBrand = (EditText) findViewById(R.id.edTxtProdBrand);
        edDesc = (EditText) findViewById(R.id.edTxtProdDesc);
        edPrice = (EditText) findViewById(R.id.edTxtProdPrice);
        edImage = (EditText) findViewById(R.id.edTxtProdImg);
        addProductBtn = (Button) findViewById(R.id.addMoreProductBtn);
        editProdBtn = (Button) findViewById(R.id.editProdBtn);

        //Adapter
        adapter = new RecyclerViewAdapter(products, (Context) this);
        rvProduct.setAdapter(adapter);
        rvProduct.setLayoutManager(new LinearLayoutManager(this));

        products.clear();
        products.addAll(db.getAllProducts());
        adapter.setListener(this);

        //Thêm SP
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị từ EditText
                String name = edName.getText().toString();
                String brand = edBrand.getText().toString();
                String desc = edDesc.getText().toString();
                double price = Double.parseDouble(edPrice.getText().toString());
                String image = edImage.getText().toString();

                if (name.isEmpty()) {
                    edName.setError("Name is required.");
                    return;
                }
                // Tạo đối tượng Product mới
                Product product = new Product(0, name, brand, desc, price, image);

                //Kiểm tra đkien thêm SP vào CSDL
                boolean checkAddProduct = db.addProduct(product);
                if (checkAddProduct) {

                    // Thêm đối tượng Product mới vào danh sách sản phẩm hiện tại trong RecyclerView
                    products.add(product);

                    // Cập nhật RecyclerView
                    resetListProduct();

                    Toast.makeText(AdminActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    void resetListProduct() {
        products.clear();
        products.addAll(db.getAllProducts());
        adapter.notifyDataSetChanged();

        //Tắt bàn phím lúc cập nhật DL
        hideKeyboard();
        //Set các editText về chuỗi rỗng
        setInputToEmpty();
    }


    //Click vào item trong list
    @Override
    public void onItemClick(Product product) {
        Toast.makeText(this, product.getId() + "", Toast.LENGTH_SHORT).show();
    }


    //Sửa SP

    @Override
    public void onItemEditClicked(Product product, int position) {

        //Ẩn nút add
        addProductBtn.setVisibility(View.GONE);
        //Hiện nút chỉnh sửa
        editProdBtn.setVisibility(View.VISIBLE);

        //Set các editText có giá trị là dữ liệu của sản phẩm được chọn để update
        edName.setText(product.getName());
        edBrand.setText(product.getBrand());
        edDesc.setText(product.getDescription());
        edImage.setText(product.getImage());
        edPrice.setText(String.valueOf(product.getPrice()));


        //Thực hiện cập nhật dữ liệu của SP
        editProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy giá trị người dùng nhập để cập nhật DL
                product.setName(edName.getText().toString());
                product.setBrand(edBrand.getText().toString());
                product.setDescription(edDesc.getText().toString());
                product.setPrice(Double.parseDouble(edPrice.getText().toString()));
                product.setImage(edImage.getText().toString());

                db.updateProduct(product);
                resetListProduct();
                addProductBtn.setVisibility(View.VISIBLE);
                editProdBtn.setVisibility(View.GONE);
                Toast.makeText(AdminActivity.this, "Update thành công", Toast.LENGTH_SHORT).show();

            }
        });


        Toast.makeText(this, "EDITED!", Toast.LENGTH_SHORT).show();
    }

    //Xóa SP
    @Override
    public void onItemDeleteClicked(Product product, int position) {


        boolean rs = db.deleteProduct(AdminActivity.this, product.getId());
        if (rs) {
            resetListProduct();
            Toast.makeText(this, "Xóa thành côngggg!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Xóa thất bại rồiiii", Toast.LENGTH_SHORT).show();

    }


    //Ẩn bàn phím ảo
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //Set các editText về chuỗi rỗng
    private void setInputToEmpty() {

        edName.setText("");
        edBrand.setText("");
        edDesc.setText("");
        edImage.setText("");
        edPrice.setText("");
    }
}