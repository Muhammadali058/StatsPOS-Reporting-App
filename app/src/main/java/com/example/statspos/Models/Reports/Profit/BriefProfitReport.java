package com.example.statspos.Models.Reports.Profit;

public class BriefProfitReport {
    String date, description, totalBills, grandTotal, grandProfit, totalMargin;

    public BriefProfitReport(){

    }

    public BriefProfitReport(String date, String description, String totalBills, String grandTotal, String grandProfit, String totalMargin) {
        this.date = date;
        this.description = description;
        this.totalBills = totalBills;
        this.grandTotal = grandTotal;
        this.grandProfit = grandProfit;
        this.totalMargin = totalMargin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(String totalBills) {
        this.totalBills = totalBills;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getGrandProfit() {
        return grandProfit;
    }

    public void setGrandProfit(String grandProfit) {
        this.grandProfit = grandProfit;
    }

    public String getTotalMargin() {
        return totalMargin;
    }

    public void setTotalMargin(String totalMargin) {
        this.totalMargin = totalMargin;
    }
}
