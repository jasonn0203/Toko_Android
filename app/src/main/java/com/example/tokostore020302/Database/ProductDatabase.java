package com.example.tokostore020302.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.tokostore020302.models.Product;
import com.example.tokostore020302.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 10;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_PRODUCTS_TABLE = "CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, brand TEXT, description TEXT, price REAL, image TEXT)";

//        String CREATE_USERS_TABLE = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastname TEXT, address TEXT, email TEXT, password TEXT)";

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

        if (rs == -1)
            return false;
        else
            return true;
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
                    user.setEmail(cursor.getString(0));
                    user.setPassword(cursor.getString(1));
                    usersList.add(user);
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        } finally {
            db.close();
        }

        return usersList;
    }

}
