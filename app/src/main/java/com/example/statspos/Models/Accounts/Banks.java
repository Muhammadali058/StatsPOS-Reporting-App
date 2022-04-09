package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class Banks {
    String id, bankName;

    public Banks() {
    }

    public Banks(String id, String bankName) {
        this.id = id;
        this.bankName = bankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @NonNull
    @Override
    public String toString() {
        return bankName;
    }
}
