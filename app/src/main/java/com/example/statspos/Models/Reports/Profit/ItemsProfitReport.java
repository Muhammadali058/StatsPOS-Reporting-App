package com.example.statspos.Models.Reports.Profit;

public class ItemsProfitReport {
    String date, itemname, cost, qty, crtn, total, profit, margin;

    public ItemsProfitReport(){

    }

    public ItemsProfitReport(String date, String itemname, String cost, String qty, String crtn, String total, String profit, String margin) {
        this.date = date;
        this.itemname = itemname;
        this.cost = cost;
        this.qty = qty;
        this.crtn = crtn;
        this.total = total;
        this.profit = profit;
        this.margin = margin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCrtn() {
        return crtn;
    }

    public void setCrtn(String crtn) {
        this.crtn = crtn;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }
}
