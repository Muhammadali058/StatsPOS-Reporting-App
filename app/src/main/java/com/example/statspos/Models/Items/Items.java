package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class Items {
    String id, itemname;

    public Items() {
    }

    public Items(String id, String itemname) {
        this.id = id;
        this.itemname = itemname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
