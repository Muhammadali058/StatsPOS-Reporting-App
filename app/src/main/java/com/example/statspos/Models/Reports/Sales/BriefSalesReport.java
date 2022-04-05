package com.example.statspos.Models.Reports.Sales;

public class BriefSalesReport {
    String date, description, totalBills, grandTotal;

    public BriefSalesReport(){

    }

    public BriefSalesReport(String date, String description, String totalBills, String grandTotal) {
        this.date = date;
        this.description = description;
        this.totalBills = totalBills;
        this.grandTotal = grandTotal;
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
}
