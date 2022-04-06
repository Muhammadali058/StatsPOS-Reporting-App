package com.example.statspos.Activities.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.statspos.Adapters.ProfitReportsFragmentAdapter;
import com.example.statspos.HP;
import com.example.statspos.R;
import com.example.statspos.databinding.ActivityProfitReportsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;

public class ProfitReportsActivity extends AppCompatActivity {

    ActivityProfitReportsBinding binding;
    ProfitReportsFragmentAdapter profitReportsFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfitReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        HP.loadSettings(this);

        binding.dateFromTB.setText(HP.getTodayDate());
        binding.dateToTB.setText(HP.getTodayDate());

        profitReportsFragmentAdapter = new ProfitReportsFragmentAdapter(this);
        binding.viewPager2.setAdapter(profitReportsFragmentAdapter);
        binding.viewPager2.setOffscreenPageLimit(8);
        binding.viewPager2.setUserInputEnabled(false);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Brief Report");
                        break;
                    case 1:
                        tab.setText("Total Report");
                        break;
                    case 2:
                        tab.setText("By Customer");
                        break;
                    case 3:
                        tab.setText("By User");
                        break;
                    case 4:
                        tab.setText("By Item");
                        break;
                    case 5:
                        tab.setText("By Category");
                        break;
                    case 6:
                        tab.setText("By Vendor");
                        break;
                    case 7:
                        tab.setText("Charts");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();

        binding.paramsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.advanceLayout.getVisibility() == View.GONE){
                    binding.advanceLayout.setVisibility(View.VISIBLE);
                }else {
                    binding.advanceLayout.setVisibility(View.GONE);
                }
            }
        });

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

    public Map<String, String> getRBParams(){
        Map<String, String> params = new HashMap<>();

        if(binding.typeRetailRB.isChecked()){
            params.put("is_retail", "1");
        }else if(binding.typeWholesaleRB.isChecked()){
            params.put("is_retail", "0");
        }

        if(binding.salesOnCashRB.isChecked()){
            params.put("sales_on", "1");
        }else if(binding.salesOnCreditRB.isChecked()){
            params.put("sales_on", "2");
        }

        if(binding.salesTypeSalesRB.isChecked()){
            params.put("sales_type", "1");
        }else if(binding.salesTypeReturnRB.isChecked()){
            params.put("sales_type", "2");
        }

        if(binding.mopCashRB.isChecked()){
            params.put("is_mop_cash_bank", "1");
        }else if(binding.mopBankRB.isChecked()){
            params.put("is_mop_cash_bank", "0");
        }

        return params;
    }

    public void setRadioButtonsVisibility(boolean visible){
        if(visible)
            binding.paramsBtn.setVisibility(View.VISIBLE);
        else
            binding.paramsBtn.setVisibility(View.GONE);
    }

}