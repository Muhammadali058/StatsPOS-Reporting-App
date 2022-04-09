package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class Expenses {
    String id, expenseName;

    public Expenses() {
    }

    public Expenses(String id, String expenseName) {
        this.id = id;
        this.expenseName = expenseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    @NonNull
    @Override
    public String toString() {
        return expenseName;
    }
}
