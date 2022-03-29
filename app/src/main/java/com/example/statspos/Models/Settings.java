package com.example.statspos.Models;

import androidx.annotation.NonNull;

public class Settings {
    String id;
    boolean saleUnderStock,stockWarning,costWarning,qtyChangeable,loadAutoCompleteItems,allowManyDuplicateBillPrints;
    boolean saleCartons,fourRatesSystem,useUrdu,isDefaultRateRetail,loadCustomerLastRate,showItemStockInSales,isPrintUrdu;

    public Settings() {

    }

    public String getId() {
        return id;
    }

    public boolean isSaleUnderStock() {
        return saleUnderStock;
    }

    public boolean isStockWarning() {
        return stockWarning;
    }

    public boolean isCostWarning() {
        return costWarning;
    }

    public boolean isQtyChangeable() {
        return qtyChangeable;
    }

    public boolean isLoadAutoCompleteItems() {
        return loadAutoCompleteItems;
    }

    public boolean isAllowManyDuplicateBillPrints() {
        return allowManyDuplicateBillPrints;
    }

    public boolean isSaleCartons() {
        return saleCartons;
    }

    public boolean isFourRatesSystem() {
        return fourRatesSystem;
    }

    public boolean isUseUrdu() {
        return useUrdu;
    }

    public boolean isDefaultRateRetail() {
        return isDefaultRateRetail;
    }

    public boolean isLoadCustomerLastRate() {
        return loadCustomerLastRate;
    }

    public boolean isShowItemStockInSales() {
        return showItemStockInSales;
    }

    public boolean isPrintUrdu() {
        return isPrintUrdu;
    }
}
