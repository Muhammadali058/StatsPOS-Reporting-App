package com.example.statspos.Adapters.Accounts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.Accounts.LedgerReportFragment;
import com.example.statspos.Fragments.BriefSalesReportFragment;
import com.example.statspos.Fragments.Sales.CategorySalesReportFragment;
import com.example.statspos.Fragments.Sales.ChartSalesFragment;
import com.example.statspos.Fragments.Sales.CustomerSalesReportFragment;
import com.example.statspos.Fragments.Sales.ItemSalesReportFragment;
import com.example.statspos.Fragments.Sales.TotalSalesReportFragment;
import com.example.statspos.Fragments.Sales.UserSalesReportFragment;
import com.example.statspos.Fragments.Sales.VendorSalesReportFragment;

public class AccountReportsFragmentAdapter extends FragmentStateAdapter {

    public AccountReportsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LedgerReportFragment();
            default:
                return new LedgerReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

}
