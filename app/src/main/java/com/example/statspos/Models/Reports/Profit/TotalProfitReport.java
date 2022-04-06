package com.example.statspos.Models.Reports.Profit;

public class TotalProfitReport {
    String date, id, customer, disc, total, profit, margin;

    public TotalProfitReport(){

    }

    public TotalProfitReport(String date, String id, String customer, String disc, String total, String profit, String margin) {
        this.date = date;
        this.id = id;
        this.customer = customer;
        this.disc = disc;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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
