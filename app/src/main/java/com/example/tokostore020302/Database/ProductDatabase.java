package com.example.tokostore020302.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tokostore020302.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db";
    private static final int DATABASE_VERSION = 1;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, brand TEXT, description TEXT, price REAL, image TEXT)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_PRODUCTS_TABLE = "DROP TABLE IF EXISTS products";
        db.execSQL(DROP_PRODUCTS_TABLE);
        onCreate(db);
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

}
