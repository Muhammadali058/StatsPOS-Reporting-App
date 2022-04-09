package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class SubExpenses {
    String id, subExpenseName;

    public SubExpenses() {
    }

    public SubExpenses(String id, String subExpenseName) {
        this.id = id;
        this.subExpenseName = subExpenseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubExpenseName() {
        return subExpenseName;
    }

    public void setSubExpenseName(String subExpenseName) {
        this.subExpenseName = subExpenseName;
    }

    @NonNull
    @Override
    public String toString() {
        return subExpenseName;
    }
}
