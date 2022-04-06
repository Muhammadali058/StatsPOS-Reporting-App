package com.example.statspos.Models.Reports.Sales;

public class TotalSalesReport {
    String date, id, customer, disc, total, salesOn, salesType, isMopCashBank, isRetail;

    public TotalSalesReport(){

    }

    public TotalSalesReport(String date, String id, String customer, String disc, String total, String salesOn, String salesType, String isMopCashBank, String isRetail) {
        this.date = date;
        this.id = id;
        this.customer = customer;
        this.disc = disc;
        this.total = total;
        this.salesOn = salesOn;
        this.salesType = salesType;
        this.isMopCashBank = isMopCashBank;
        this.isRetail = isRetail;
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

    public String getSalesOn() {
        return salesOn;
    }

    public void setSalesOn(String salesOn) {
        this.salesOn = salesOn;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }

    public String getIsMopCashBank() {
        return isMopCashBank;
    }

    public void setIsMopCashBank(String isMopCashBank) {
        this.isMopCashBank = isMopCashBank;
    }

    public String getIsRetail() {
        return isRetail;
    }

    public void setIsRetail(String isRetail) {
        this.isRetail = isRetail;
    }
}
