package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class SubCategories {
    int id;
    String sub_category_name;

    public SubCategories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    @NonNull
    @Override
    public String toString() {
        return sub_category_name;
    }
}
