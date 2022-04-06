package com.example.statspos.Activities.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.statspos.Adapters.StockReportsFragmentAdapter;
import com.example.statspos.HP;
import com.example.statspos.databinding.ActivityStockReportsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.HashMap;
import java.util.Map;

public class StockReportsActivity extends AppCompatActivity {

    ActivityStockReportsBinding binding;
    StockReportsFragmentAdapter stockReportsFragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        HP.loadSettings(this);

        binding.dateTB.setText(HP.getTodayDate());

        stockReportsFragmentAdapter = new StockReportsFragmentAdapter(this);
        binding.viewPager2.setAdapter(stockReportsFragmentAdapter);
        binding.viewPager2.setOffscreenPageLimit(4);
        binding.viewPager2.setUserInputEnabled(false);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Total Report");
                        break;
                    case 1:
                        tab.setText("By Item");
                        break;
                    case 2:
                        tab.setText("By Category");
                        break;
                    case 3:
                        tab.setText("By Vendor");
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

        binding.dateTB.setOnClickListener(new HP.OnDateClickListener(this, new HP.OnDateSet() {
            @Override
            public void onDateSet(String date) {
                binding.dateTB.setText(date);
            }
        }));

    }

    public Map<String, String> getDateParams(){
        Map<String, String> params = new HashMap<>();

        params.put("date", HP.reverseDate(binding.dateTB.getText().toString()));

        return params;
    }

    public Map<String, String> getRBParams(){
        Map<String, String> params = new HashMap<>();

        if(binding.costRB.isChecked()){
            params.put("stock_at", "cost");
        }else if(binding.retailRB.isChecked()){
            params.put("stock_at", "retail");
        }else if(binding.wholesaleRB.isChecked()){
            params.put("stock_at", "w_sale");
        }

        return params;
    }

}