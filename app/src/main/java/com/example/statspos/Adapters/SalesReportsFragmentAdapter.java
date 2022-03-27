package com.example.statspos.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.BriefSalesReportFragment;
import com.example.statspos.Fragments.ByCategorySalesReportFragment;
import com.example.statspos.Fragments.ByCustomerSalesReportFragment;
import com.example.statspos.Fragments.ByItemSalesReportFragment;
import com.example.statspos.Fragments.ByUserSalesReportFragment;
import com.example.statspos.Fragments.ByVendorSalesReportFragment;
import com.example.statspos.Fragments.TotalSalesReportFragment;

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
                return new ByCustomerSalesReportFragment();
            case 3:
                return new ByUserSalesReportFragment();
            case 4:
                return new ByItemSalesReportFragment();
            case 5:
                return new ByCategorySalesReportFragment();
            case 6:
                return new ByVendorSalesReportFragment();
            default:
                return new BriefSalesReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
