package com.example.statspos.Models.Accounts;

import androidx.annotation.NonNull;

public class SubBanks {
    String id, subBankName;

    public SubBanks() {
    }

    public SubBanks(String id, String subBankName) {
        this.id = id;
        this.subBankName = subBankName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubBankName() {
        return subBankName;
    }

    public void setSubBankName(String subBankName) {
        this.subBankName = subBankName;
    }

    @NonNull
    @Override
    public String toString() {
        return subBankName;
    }
}
