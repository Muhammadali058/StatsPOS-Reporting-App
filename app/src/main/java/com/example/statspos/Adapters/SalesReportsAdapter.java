package com.example.statspos.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.Reports.BriefSalesReportFragment;
import com.example.statspos.Fragments.TotalSalesReportFragment;

public class SalesReportsAdapter extends FragmentStateAdapter {
    public SalesReportsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TotalSalesReportFragment();
            case 1:
                return new BriefSalesReportFragment();
            default:
                return new TotalSalesReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
