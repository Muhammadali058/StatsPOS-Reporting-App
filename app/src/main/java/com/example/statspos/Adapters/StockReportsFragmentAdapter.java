package com.example.statspos.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.BriefSalesReportFragment;
import com.example.statspos.Fragments.Sales.CategorySalesReportFragment;
import com.example.statspos.Fragments.Sales.ChartSalesFragment;
import com.example.statspos.Fragments.Sales.CustomerSalesReportFragment;
import com.example.statspos.Fragments.Sales.ItemSalesReportFragment;
import com.example.statspos.Fragments.Sales.TotalSalesReportFragment;
import com.example.statspos.Fragments.Sales.UserSalesReportFragment;
import com.example.statspos.Fragments.Sales.VendorSalesReportFragment;
import com.example.statspos.Fragments.Stock.CategoryStockReportFragment;
import com.example.statspos.Fragments.Stock.ItemStockReportFragment;
import com.example.statspos.Fragments.Stock.TotalStockReportFragment;
import com.example.statspos.Fragments.Stock.VendorStockReportFragment;

public class StockReportsFragmentAdapter extends FragmentStateAdapter {

    public StockReportsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TotalStockReportFragment();
            case 1:
                return new ItemStockReportFragment();
            case 2:
                return new CategoryStockReportFragment();
            case 3:
                return new VendorStockReportFragment();
            default:
                return new TotalStockReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
