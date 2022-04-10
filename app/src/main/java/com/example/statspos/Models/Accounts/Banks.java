package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class Banks {
    int id;
    String bank_name;

    public Banks() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    @NonNull
    @Override
    public String toString() {
        return bank_name;
    }
}
