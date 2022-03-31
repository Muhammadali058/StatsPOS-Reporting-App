package com.example.statspos.Models.Reports;

public class ChartSalesReport {
    String date, total;

    public ChartSalesReport(){

    }

    public ChartSalesReport(String date, String total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
