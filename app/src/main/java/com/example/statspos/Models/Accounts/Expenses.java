package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class Expenses {
    int id;
    String expense_name;

    public Expenses() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpense_name() {
        return expense_name;
    }

    public void setExpense_name(String expense_name) {
        this.expense_name = expense_name;
    }

    @NonNull
    @Override
    public String toString() {
        return expense_name;
    }
}
