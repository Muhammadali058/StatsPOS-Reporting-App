package com.example.statspos.Models;

import androidx.annotation.NonNull;

public class Customers {
    String id, customerName;

    public Customers() {

    }

    public Customers(String id, String customerName) {
        this.id = id;
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @NonNull
    @Override
    public String toString() {
        return customerName;
    }
}
