package com.example.statspos.Models.Items;

import androidx.annotation.NonNull;

public class SubCategories {
    String id, subCategoryName;

    public SubCategories() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    @NonNull
    @Override
    public String toString() {
        return subCategoryName;
    }
}
