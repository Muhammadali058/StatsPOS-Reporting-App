package com.example.statspos.Adapters.Sales;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.BriefSalesReportFragment;
import com.example.statspos.Fragments.Sales.CategorySalesReportFragment;
import com.example.statspos.Fragments.Sales.ChartSalesFragment;
import com.example.statspos.Fragments.Sales.CustomerSalesReportFragment;
import com.example.statspos.Fragments.Sales.ItemSalesReportFragment;
import com.example.statspos.Fragments.Sales.UserSalesReportFragment;
import com.example.statspos.Fragments.Sales.VendorSalesReportFragment;
import com.example.statspos.Fragments.Sales.TotalSalesReportFragment;

public class SalesReportsFragmentAdapter extends FragmentStateAdapter {

    public SalesReportsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BriefSalesReportFragment();
            case 1:
                return new TotalSalesReportFragment();
            case 2:
                return new CustomerSalesReportFragment();
            case 3:
                return new UserSalesReportFragment();
            case 4:
                return new ItemSalesReportFragment();
            case 5:
                return new CategorySalesReportFragment();
            case 6:
                return new VendorSalesReportFragment();
            case 7:
                return new ChartSalesFragment();
            default:
                return new BriefSalesReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

}
