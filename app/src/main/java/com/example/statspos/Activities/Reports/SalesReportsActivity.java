package com.example.statspos.Activities.Reports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.statspos.Adapters.Reports.TotalSalesReportAdapter;
import com.example.statspos.Adapters.SalesReportsAdapter;
import com.example.statspos.Fragments.Reports.BriefSalesReportFragment;
import com.example.statspos.Fragments.TotalSalesReportFragment;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.TotalSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.ActivitySalesReportsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReportsActivity extends AppCompatActivity {

    ActivitySalesReportsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalesReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        SalesReportsAdapter salesReportsAdapter = new SalesReportsAdapter(this);
        binding.viewPager2.setAdapter(salesReportsAdapter);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Total Sales Report");
                        break;
                    case 1:
                        tab.setText("Brief Sales Report");
                        break;
                }
            }
        });

        tabLayoutMediator.attach();
    }

    private void init(){
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
    }

    public String getDateFrom(){
        return binding.dateFromTB.getText().toString();
    }

    public String getDateTo(){
        return binding.dateToTB.getText().toString();
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

    public void setRadioButtonsVisivility(boolean visible){
        if(visible)
            binding.paramsBtn.setVisibility(View.VISIBLE);
        else
            binding.paramsBtn.setVisibility(View.GONE);
    }
}