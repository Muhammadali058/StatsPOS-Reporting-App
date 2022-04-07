package com.example.statspos.Models.Accounts;

public class IncomeStatement {
    String naration, expense, sales, cgs, grossProfit, totalExpenses, netProfit;

    public IncomeStatement() {
    }

    public IncomeStatement(String naration, String expense, String sales, String cgs, String grossProfit, String totalExpenses, String netProfit) {
        this.naration = naration;
        this.expense = expense;
        this.sales = sales;
        this.cgs = cgs;
        this.grossProfit = grossProfit;
        this.totalExpenses = totalExpenses;
        this.netProfit = netProfit;
    }

    public String getNaration() {
        return naration;
    }

    public void setNaration(String naration) {
        this.naration = naration;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getCgs() {
        return cgs;
    }

    public void setCgs(String cgs) {
        this.cgs = cgs;
    }

    public String getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(String grossProfit) {
        this.grossProfit = grossProfit;
    }

    public String getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(String totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }
}
