package com.example.statspos.Models.Reports.Accounts;

public class Ledger {
    String date, naration, debit, credit, oldBalance, newBalance, balance;

    public Ledger() {
    }

    public Ledger(String date, String naration, String debit, String credit, String oldBalance, String newBalance, String balance) {
        this.date = date;
        this.naration = naration;
        this.debit = debit;
        this.credit = credit;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
        this.balance = balance;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNaration() {
        return naration;
    }

    public void setNaration(String naration) {
        this.naration = naration;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getOldBalance() {
        return oldBalance;
    }

    public void setOldBalance(String oldBalance) {
        this.oldBalance = oldBalance;
    }

    public String getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(String newBalance) {
        this.newBalance = newBalance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
