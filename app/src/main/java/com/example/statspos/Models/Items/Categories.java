package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class Categories {
    String id, categoryName;

    public Categories() {
    }

    public Categories(String id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @NonNull
    @Override
    public String toString() {
        return categoryName;
    }
}
