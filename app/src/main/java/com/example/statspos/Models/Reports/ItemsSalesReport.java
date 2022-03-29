package com.example.statspos.Models.Reports;

public class ItemsSalesReport {
    String date, itemname, pcs, crtn, rate, crtnRate, disc, total;

    public ItemsSalesReport(){

    }

    public ItemsSalesReport(String date, String itemname, String pcs, String crtn, String rate, String crtnRate, String disc, String total) {
        this.date = date;
        this.itemname = itemname;
        this.pcs = pcs;
        this.crtn = crtn;
        this.rate = rate;
        this.crtnRate = crtnRate;
        this.disc = disc;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getCrtn() {
        return crtn;
    }

    public void setCrtn(String crtn) {
        this.crtn = crtn;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCrtnRate() {
        return crtnRate;
    }

    public void setCrtnRate(String crtnRate) {
        this.crtnRate = crtnRate;
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
}
