package com.example.statspos.Models.Reports.Purchase;

public class TotalPurchaseReport {
    String date, id, vendor, total;

    public TotalPurchaseReport() {
    }

    public TotalPurchaseReport(String date, String id, String vendor, String total) {
        this.date = date;
        this.id = id;
        this.vendor = vendor;
        this.total = total;
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
