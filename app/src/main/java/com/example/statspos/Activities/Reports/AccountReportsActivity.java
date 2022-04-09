package com.example.statspos.Activities.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.statspos.Adapters.Accounts.AccountReportsFragmentAdapter;
import com.example.statspos.Adapters.Sales.SalesReportsFragmentAdapter;
import com.example.statspos.HP;
import com.example.statspos.R;
import com.example.statspos.databinding.ActivityAccountReportsBinding;
import com.example.statspos.databinding.ActivitySalesReportsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;

public class AccountReportsActivity extends AppCompatActivity {

    ActivityAccountReportsBinding binding;
    AccountReportsFragmentAdapter accountReportsFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        HP.loadSettings(this);

        binding.dateFromTB.setText(HP.getTodayDate());
        binding.dateToTB.setText(HP.getTodayDate());

        accountReportsFragmentAdapter = new AccountReportsFragmentAdapter(this);
        binding.viewPager2.setAdapter(accountReportsFragmentAdapter);
        binding.viewPager2.setOffscreenPageLimit(7);
        binding.viewPager2.setUserInputEnabled(false);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Journal");
                        break;
                    case 1:
                        tab.setText("Ledger");
                        break;
                    case 2:
                        tab.setText("Expenses");
                        break;
                    case 3:
                        tab.setText("Bank Statement");
                        break;
                    case 4:
                        tab.setText("Cash Account");
                        break;
                    case 5:
                        tab.setText("Receipts/Payments");
                        break;
                    case 6:
                        tab.setText("Debtors/Creditors");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        binding.dateFromTB.setOnClickListener(new HP.OnDateClickListener(this, new HP.OnDateSet() {
            @Override
            public void onDateSet(String date) {
                binding.dateFromTB.setText(date);
            }
        }));

        binding.dateToTB.setOnClickListener(new HP.OnDateClickListener(this, new HP.OnDateSet() {
            @Override
            public void onDateSet(String date) {
                binding.dateToTB.setText(date);
            }
        }));
    }

    public Map<String, String> getDateParams(){
        Map<String, String> params = new HashMap<>();

        params.put("date_from", HP.reverseDate(binding.dateFromTB.getText().toString()));
        params.put("date_to", HP.reverseDate(binding.dateToTB.getText().toString()));

        return params;
    }

    public void setDateLayoutVisibility(boolean visible){
        if(visible)
            binding.dateLayout.setVisibility(View.VISIBLE);
        else
            binding.dateLayout.setVisibility(View.GONE);
    }

}