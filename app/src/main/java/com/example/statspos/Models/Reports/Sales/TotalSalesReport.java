package com.example.statspos.Models.Reports.Sales;

public class TotalSalesReport {
    String date, id, customer, total;

    public TotalSalesReport(){

    }

    public TotalSalesReport(String date, String id, String customer, String total) {
        this.date = date;
        this.id = id;
        this.customer = customer;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
