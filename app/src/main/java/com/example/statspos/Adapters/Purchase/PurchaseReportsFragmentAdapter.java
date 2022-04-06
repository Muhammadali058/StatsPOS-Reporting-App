package com.example.statspos.Adapters.Purchase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.BriefSalesReportFragment;
import com.example.statspos.Fragments.Purchase.BriefPurchaseReportFragment;
import com.example.statspos.Fragments.Purchase.CategoryPurchaseReportFragment;
import com.example.statspos.Fragments.Purchase.ChartPurchaseFragment;
import com.example.statspos.Fragments.Purchase.ItemPurchaseReportFragment;
import com.example.statspos.Fragments.Purchase.TotalPurchaseReportFragment;
import com.example.statspos.Fragments.Purchase.UserPurchaseReportFragment;
import com.example.statspos.Fragments.Purchase.VendorPurchaseReportFragment;
import com.example.statspos.Fragments.Sales.CategorySalesReportFragment;
import com.example.statspos.Fragments.Sales.ChartSalesFragment;
import com.example.statspos.Fragments.Sales.CustomerSalesReportFragment;
import com.example.statspos.Fragments.Sales.ItemSalesReportFragment;
import com.example.statspos.Fragments.Sales.TotalSalesReportFragment;
import com.example.statspos.Fragments.Sales.UserSalesReportFragment;
import com.example.statspos.Fragments.Sales.VendorSalesReportFragment;

public class PurchaseReportsFragmentAdapter  extends FragmentStateAdapter {

    public PurchaseReportsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BriefPurchaseReportFragment();
            case 1:
                return new TotalPurchaseReportFragment();
            case 2:
                return new VendorPurchaseReportFragment();
            case 3:
                return new UserPurchaseReportFragment();
            case 4:
                return new ItemPurchaseReportFragment();
            case 5:
                return new CategoryPurchaseReportFragment();
            case 6:
                return new ChartPurchaseFragment();
            default:
                return new BriefPurchaseReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
