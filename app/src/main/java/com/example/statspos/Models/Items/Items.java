package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class Items {
    int id;
    String itemname, barcode, ref_code;
    float cost, retail, w_sale, crtn_rate;

    public Items() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRef_code() {
        return ref_code;
    }

    public void setRef_code(String ref_code) {
        this.ref_code = ref_code;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getRetail() {
        return retail;
    }

    public void setRetail(float retail) {
        this.retail = retail;
    }

    public float getW_sale() {
        return w_sale;
    }

    public void setW_sale(float w_sale) {
        this.w_sale = w_sale;
    }

    public float getCrtn_rate() {
        return crtn_rate;
    }

    public void setCrtn_rate(float crtn_rate) {
        this.crtn_rate = crtn_rate;
    }

    @NonNull
    @Override
    public String toString() {
        return itemname;
    }
}
