package com.example.statspos.Models.Reports.Accounts;

public class DebtorsCreditors {
    String accountName, balance;

    public DebtorsCreditors() {
    }

    public DebtorsCreditors(String accountName, String balance) {
        this.accountName = accountName;
        this.balance = balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
