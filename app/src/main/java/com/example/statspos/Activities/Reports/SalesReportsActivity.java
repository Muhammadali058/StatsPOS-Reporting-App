package com.example.statspos.Activities.Reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.statspos.Fragments.Reports.BriefSalesReportFragment;
import com.example.statspos.Fragments.TotalSalesReportFragment;
import com.example.statspos.R;
import com.example.statspos.databinding.ActivitySalesReportsBinding;

import java.util.ArrayList;
import java.util.List;

public class SalesReportsActivity extends AppCompatActivity {

    List<String> spinnerList;
    ActivitySalesReportsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalesReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spinnerList = new ArrayList<>();
        spinnerList.add("Brief Sales Report");
        spinnerList.add("Total Sales Report");
        spinnerList.add("Customer Wise Report");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, spinnerList);
        binding.spinner.setAdapter(arrayAdapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        loadFragment(new BriefSalesReportFragment());
                        break;
                    case 1:
                        loadFragment(new TotalSalesReportFragment());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
//        fragmentTransaction.addToBackStack(null);
    }
}