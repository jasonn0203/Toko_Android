package com.example.tokostore020302.ui;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;
import java.io.InputStream;

//Global share dùng chung
public class SharedUtils {
    public static final String SHARE_PREFERENCES_APP = "shared_preferences_app";
    public static final String SHARE_KEY_USER = "key_user";
    public static final String IS_GOOGLE_LOGIN = "isGoogleLogin";

    public static final String SHARE_KEY_LATEST_USER = "key_user_latest";


    //CSDL biến dùng chung
    public static final String DATABASE_NAME = "TokoDB";
    public static final String TABLE_PRODUCT = "Product";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_BRAND = "brand";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_IMAGE = "image";


    public static Bitmap convertToBitmapFromAssets(Context context, String imageName) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("images/" + imageName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
