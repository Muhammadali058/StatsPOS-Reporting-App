package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class Items {
    int id;
    String itemname;

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

    @NonNull
    @Override
    public String toString() {
        return itemname;
    }
}
