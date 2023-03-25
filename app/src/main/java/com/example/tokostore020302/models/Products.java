package com.example.tokostore020302.models;

import java.io.Serializable;

public class Products implements Serializable {
    private String prodID;
    private String brand;


    private String prodName;
    private String prodDesc;
    private int prodPrice;
    private int prodImg;


    //------------GETTER  - SETTER-------------------
    public String getProdID() {
        return prodID;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String category) {
        this.brand = category;
    }

    public int getProdImg() {
        return prodImg;
    }

    public void setProdImg(int prodImg) {
        this.prodImg = prodImg;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public Integer getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Integer prodPrice) {
        this.prodPrice = prodPrice;
    }

    //----------------CONSTRUCTOR-----------------------


    public Products(String prodID, String brand, String prodName, String prodDesc, Integer prodPrice, Integer prodImg) {
        this.prodID = prodID;
        this.brand = brand;
        this.prodName = prodName;
        this.prodDesc = prodDesc;
        this.prodPrice = prodPrice;
        this.prodImg = prodImg;
    }

    public Products(String prodID, String brand, String prodName, Integer prodPrice, Integer prodImg) {
        this.prodID = prodID;
        this.brand = brand;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
        this.prodImg = prodImg;
    }
}
