package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class Vendors {
    String id, vendorName;

    public Vendors() {

    }

    public Vendors(String id, String vendorName) {
        this.id = id;
        this.vendorName = vendorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @NonNull
    @Override
    public String toString() {
        return vendorName;
    }
}
