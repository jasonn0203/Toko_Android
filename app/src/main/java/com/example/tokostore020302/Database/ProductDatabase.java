package com.example.tokostore020302.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.tokostore020302.models.Cart;
import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 16;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_PRODUCTS_TABLE = "CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, brand TEXT, description TEXT, price REAL, image TEXT)";

//        String CREATE_USERS_TABLE = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastname TEXT, address TEXT, email TEXT, password TEXT)";

        /*String CREATE_CART_TABLE = "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, quantity INTEGER, FOREIGN KEY (product_id) REFERENCES products(id))";
        db.execSQL(CREATE_CART_TABLE);*/

/*        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='cart'", null);
        if (cursor.getCount() == 0) {
            String CREATE_CART_TABLE = "CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, quantity INTEGER, FOREIGN KEY (product_id) REFERENCES products(id))";
            db.execSQL(CREATE_CART_TABLE);
        }
        cursor.close();*/


        //db.execSQL(CREATE_PRODUCTS_TABLE);
        //db.execSQL(CREATE_USERS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";
//        db.execSQL(DROP_USERS_TABLE);
//        onCreate(db);
    }


    //Thêm SP
    public boolean addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("brand", product.getBrand());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());

        long rs = db.insert("products", null, values);

        if (rs == -1) return false;
        else return true;
    }


    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("brand", product.getBrand());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());

        db.update("products", values, "id = ?", new String[]{String.valueOf(product.getId())});
    }

    public boolean deleteProduct(Context context, int id) {
        SQLiteDatabase db = getWritableDatabase();

        int rs = db.delete("products", "id = ?", new String[]{String.valueOf(id)});

        return (rs > 0);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products", null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String brand = cursor.getString(2);
            String description = cursor.getString(3);
            double price = cursor.getDouble(4);
            String image = cursor.getString(5);
            products.add(new Product(id, name, brand, description, price, image));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return products;

    }


    //BẢNG users
    public boolean registerUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("firstname", user.getFirstname());
        values.put("lastname", user.getLastname());
        values.put("address", user.getAddress());
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        db.insert("users", null, values);

        return true;
    }

    public ArrayList<User> loginUser(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<User> usersList = new ArrayList<User>();

        try {
            String query = "SELECT * FROM users WHERE email=? AND password=?";
            String[] selectionArgs = {email, password};
            Cursor cursor = db.rawQuery(query, selectionArgs);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setFirstname(cursor.getString(cursor.getColumnIndexOrThrow("firstname")));
                    user.setLastname(cursor.getString(cursor.getColumnIndexOrThrow("lastname")));
                    user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                    usersList.add(user);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        } finally {
            db.close();
        }

        return usersList;
    }

    public void updatePassword(User user) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{user.getEmail()});
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());

        db.update("users", values, "email=?", new String[]{user.getEmail()});
    }

    // Phương thức kiểm tra sự tồn tại của email trong cơ sở dữ liệu
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=?", new String[]{email});
        //TH có tồn tại
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public String getUserAddress(int userId) {
        String query = "SELECT address FROM users WHERE id = ?";
        SQLiteDatabase db = getReadableDatabase();
        String address = null;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
        }
        cursor.close();
        return address;
    }



    //BẢNG CART

    //thêm vào giỏ
    public void addToCart(Product product, int quantity) {
        SQLiteDatabase db = getWritableDatabase();

        if (quantity == 10) {
            return;
        } else {
            Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE product_id=?", new String[]{String.valueOf(product.getId())});
            if (cursor.getCount() > 0) { // sản phẩm đã có trong giỏ hàng
                cursor.moveToFirst();
                int currentQuantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                if (currentQuantity == 10) {
                    return;
                } else {
                    Cart cartItem = new Cart(product, currentQuantity + 1);
                    updateCartItem(cartItem); // cập nhật lại giỏ hàng với số lượng mới
                }
            } else { // sản phẩm chưa có trong giỏ hàng
                ContentValues values = new ContentValues();
                values.put("product_id", product.getId());
                values.put("quantity", quantity);
                db.insert("cart", null, values);
            }
        }
        db.close();
    }


    //xóa khỏi giỏ hàng
    public void removeFromCart(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "product_id = ?", new String[]{String.valueOf(product.getId())});
        db.close();
    }

    //Cập nhật giỏ hàng
    public void updateCartItem(Cart cartItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", cartItem.getQuantity());
        db.update("cart", values, "product_id = ?", new String[]{String.valueOf(cartItem.getProduct().getId())});
        db.close();
    }

    //lấy ra danh sách giỏ hàng

    public ArrayList<Cart> getCartContents() {
        ArrayList<Cart> cartContents = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT products.*, cart.product_id, cart.quantity FROM products INNER JOIN cart ON products.id = cart.product_id", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String brand = cursor.getString(cursor.getColumnIndexOrThrow("brand"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
            Product product = new Product(id, name, brand, description, price, image);
            Cart cartItem = new Cart(product, quantity);
            cartContents.add(cartItem);
        }
        cursor.close();
        db.close();
        return cartContents;
    }

    //Tính tổng giá tiền trong đơn hàng
    public double calculateTotalPrice() {
        String query = "SELECT SUM(products.price * cart.quantity) AS total_price FROM cart JOIN products ON cart.product_id = products.id";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        double totalPrice = 0.0;
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow("total_price"));
        }
        cursor.close();
        return totalPrice;
    }
}
