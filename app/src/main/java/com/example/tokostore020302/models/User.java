package com.example.tokostore020302.models;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    String firstname;
    String lastname;
    String email;
    String address;
    String password;

    public User() {

    }

    public User(int id, String firstname, String lastname, String email, String address, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
