package com.example.statspos.Models.Reports.Purchase;

public class TotalPurchaseReport {
    String date, id, vendor, disc, total, purchaseOn, purchaseType, isMopCashBank;

    public TotalPurchaseReport() {
    }

    public TotalPurchaseReport(String date, String id, String vendor, String disc, String total, String purchaseOn, String purchaseType, String isMopCashBank) {
        this.date = date;
        this.id = id;
        this.vendor = vendor;
        this.disc = disc;
        this.total = total;
        this.purchaseOn = purchaseOn;
        this.purchaseType = purchaseType;
        this.isMopCashBank = isMopCashBank;
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

    public String getPurchaseOn() {
        return purchaseOn;
    }

    public void setPurchaseOn(String purchaseOn) {
        this.purchaseOn = purchaseOn;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getIsMopCashBank() {
        return isMopCashBank;
    }

    public void setIsMopCashBank(String isMopCashBank) {
        this.isMopCashBank = isMopCashBank;
    }
}
