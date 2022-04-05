package com.example.statspos.Models.Reports;

public class ChartReport {
    String date, total;

    public ChartReport(){

    }

    public ChartReport(String date, String total) {
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
