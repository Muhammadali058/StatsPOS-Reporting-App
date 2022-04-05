package com.example.statspos.Models.Reports.Purchase;

public class ItemsPurchaseReport {
    String date, itemname, cost, qty, crtn, crtnSize, disc, total;

    public ItemsPurchaseReport() {
    }

    public ItemsPurchaseReport(String date, String itemname, String cost, String qty, String crtn, String crtnSize, String disc, String total) {
        this.date = date;
        this.itemname = itemname;
        this.cost = cost;
        this.qty = qty;
        this.crtn = crtn;
        this.crtnSize = crtnSize;
        this.disc = disc;
        this.total = total;
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

    public String getCrtnSize() {
        return crtnSize;
    }

    public void setCrtnSize(String crtnSize) {
        this.crtnSize = crtnSize;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
