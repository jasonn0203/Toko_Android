package com.example.tokostore020302.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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


    ImageButton searchBtn;
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

    //Đính kèm database
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
        txtProduct = (TextView) view.findViewById(R.id.txtNewProducts);
        searchInput = (EditText) view.findViewById(R.id.searchInput);
        searchBtn = (ImageButton) view.findViewById(R.id.btnSearch);
        noProdFoundImg = (ImageView) view.findViewById(R.id.img_no_product);


        rvHomeProduct = (RecyclerView) view.findViewById(R.id.product_grid);

        //Adapter
        adapter = new HomeProductAdapter(products, context, this);
        rvHomeProduct.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        rvHomeProduct.setLayoutManager(layoutManager);

        //Clear dữ liệu cũ trước khi render ra
        products.clear();
        //Load dữ liệu vào ds
        products.addAll(db.getAllProducts());
        adapter.notifyDataSetChanged();
        adapter.setListener(this);

        //Slider
        Banner((ViewFlipper) view.findViewById(R.id.simpleViewFlipper));


        //Search sản phẩm
        searchInput.addTextChangedListener(queryOnSearchInput());


        return view;
    }


    //Chức năng search sản phẩm
    private void getQueryOnSearchInput(String query) {
        //Khởi tạo biến để lưu giá trị có hay không tìm thấy sản phẩm
        boolean founded = false;

        //Tạo danh sách chứa sản phẩm trùng khớp với KQ search
        List<Product> searchValues = new ArrayList<>();
        //Nếu có kết quả tìm thấy thì làm
        if (products != null) {
            //Duyệt trong DB
            for (Product product : products) {
                //Lấy ra tên của SP trong DB mà trùng khớp
                String productGetName = product.getName().toLowerCase();
                //Trường hợp trùng khớp kết quả
                if (productGetName.contains(query)) {
                    noProdFoundImg.setVisibility(View.GONE);
                    searchValues.add(product);
                    founded = true;
                }
            }
        }


        //TH không trùng khớp
        if (!founded) {
            noProdFoundImg.setImageResource(R.drawable.no_product_found);
            noProdFoundImg.setVisibility(View.VISIBLE);
            txtProduct.setText("Không tìm thấy kết quả phù hợp với '" + query + "'");
        } else {
            txtProduct.setText("Kết quả tìm kiếm của '" + query + "'");
        }

        //Cập nhật adapter
        adapter = new HomeProductAdapter(searchValues, context, this);
        rvHomeProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


    @NonNull
    private TextWatcher queryOnSearchInput() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                //Thực hiện tìm kiếm SP
                getQueryOnSearchInput(editable.toString());

                //TH ô input rỗng
                if (TextUtils.isEmpty(editable.toString())) {
                    txtProduct.setText("Sản phẩm mới nhất dành cho bạn !");
                }

            }
        };
    }

    private void Banner(ViewFlipper view) {
        //Slider quảng cáo
        ViewFlipper simpleViewFlipper = view;
        simpleViewFlipper.setAutoStart(true);
    }


    //Click vào để đến trang detail
    @Override
    public void onItemClick(Product product) {
        ProductDetailFragment detailFragment = new ProductDetailFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //Tạo bundle để gửi dữ liệu đến trang detail
        Bundle bundle = new Bundle();
        bundle.putString("name", product.getName());
        bundle.putString("desc", product.getDescription());
        bundle.putDouble("price", product.getPrice());
        bundle.putString("image", product.getImage());
        detailFragment.setArguments(bundle);


    }


}
