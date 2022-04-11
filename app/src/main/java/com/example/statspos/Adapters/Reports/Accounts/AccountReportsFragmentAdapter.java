package com.example.statspos.Adapters.Reports.Accounts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.statspos.Fragments.Accounts.BankStatementFragment;
import com.example.statspos.Fragments.Accounts.CashAccountFragment;
import com.example.statspos.Fragments.Accounts.DebtorsCreditorsFragment;
import com.example.statspos.Fragments.Accounts.ExpenseReportFragment;
import com.example.statspos.Fragments.Accounts.JournalReportFragment;
import com.example.statspos.Fragments.Accounts.LedgerReportFragment;
import com.example.statspos.Fragments.Accounts.ReceiptsPaymentsFragment;

public class AccountReportsFragmentAdapter extends FragmentStateAdapter {

    public AccountReportsFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new JournalReportFragment();
            case 1:
                return new LedgerReportFragment();
            case 2:
                return new ExpenseReportFragment();
            case 3:
                return new BankStatementFragment();
            case 4:
                return new CashAccountFragment();
            case 5:
                return new ReceiptsPaymentsFragment();
            case 6:
                return new DebtorsCreditorsFragment();
            default:
                return new JournalReportFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

}
