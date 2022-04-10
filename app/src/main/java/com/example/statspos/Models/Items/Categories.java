package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class Categories {
    int id;
    String category_name;

    public Categories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    @NonNull
    @Override
    public String toString() {
        return category_name;
    }
}
