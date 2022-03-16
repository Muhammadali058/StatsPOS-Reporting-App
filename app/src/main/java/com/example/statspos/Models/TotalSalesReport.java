package com.example.statspos.Models;

public class TotalSalesReport {
    String date, invoiceNo, customer, total;

    public TotalSalesReport(String date, String invoiceNo, String customer, String total) {
        this.date = date;
        this.invoiceNo = invoiceNo;
        this.customer = customer;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
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
