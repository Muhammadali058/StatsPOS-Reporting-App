package com.example.statspos.Adapters.Reports.Profit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.Profit.BriefProfitReportFragment;
import com.example.statspos.Fragments.Profit.CategoryProfitReportFragment;
import com.example.statspos.Fragments.Profit.ChartProfitFragment;
import com.example.statspos.Fragments.Profit.CustomerProfitReportFragment;
import com.example.statspos.Fragments.Profit.ItemProfitReportFragment;
import com.example.statspos.Fragments.Profit.TotalProfitReportFragment;
import com.example.statspos.Fragments.Profit.UserProfitReportFragment;
import com.example.statspos.Fragments.Profit.VendorProfitReportFragment;

public class ProfitReportsFragmentAdapter extends FragmentStateAdapter {

    public ProfitReportsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BriefProfitReportFragment();
            case 1:
                return new TotalProfitReportFragment();
            case 2:
                return new CustomerProfitReportFragment();
            case 3:
                return new UserProfitReportFragment();
            case 4:
                return new ItemProfitReportFragment();
            case 5:
                return new CategoryProfitReportFragment();
            case 6:
                return new VendorProfitReportFragment();
            case 7:
                return new ChartProfitFragment();
            default:
                return new BriefProfitReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }

}
