package com.example.tokostore020302.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cart  implements Parcelable {
    private Product product;
    private int quantity;

    public Cart(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    protected Cart(Parcel in) {
        quantity = in.readInt();
        product = (Product) in.readValue(Product.class.getClassLoader());
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeValue(product);
    }
}

