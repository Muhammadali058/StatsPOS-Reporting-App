package com.example.statspos.Models.Reports;

public class StockReport {
    String itemname, stock_pcs, stock_crtn, rate, rateHead, stockValue;

    public StockReport() {
    }

    public StockReport(String itemname, String stock_pcs, String stock_crtn, String rate, String rateHead, String stockValue) {
        this.itemname = itemname;
        this.stock_pcs = stock_pcs;
        this.stock_crtn = stock_crtn;
        this.rate = rate;
        this.rateHead = rateHead;
        this.stockValue = stockValue;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getStock_pcs() {
        return stock_pcs;
    }

    public void setStock_pcs(String stock_pcs) {
        this.stock_pcs = stock_pcs;
    }

    public String getStock_crtn() {
        return stock_crtn;
    }

    public void setStock_crtn(String stock_crtn) {
        this.stock_crtn = stock_crtn;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateHead() {
        return rateHead;
    }

    public void setRateHead(String rateHead) {
        this.rateHead = rateHead;
    }

    public String getStockValue() {
        return stockValue;
    }

    public void setStockValue(String stockValue) {
        this.stockValue = stockValue;
    }
}
