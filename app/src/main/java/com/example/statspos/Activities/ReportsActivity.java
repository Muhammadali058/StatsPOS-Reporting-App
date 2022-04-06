package com.example.statspos.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.statspos.Activities.Reports.ProfitReportsActivity;
import com.example.statspos.Activities.Reports.PurchaseReportsActivity;
import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Activities.Reports.StockReportsActivity;
import com.example.statspos.R;
import com.example.statspos.databinding.ActivityReportsBinding;

public class ReportsActivity extends AppCompatActivity {

    ActivityReportsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        binding.salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this, SalesReportsActivity.class);
                startActivity(intent);
            }
        });

        binding.purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this, PurchaseReportsActivity.class);
                startActivity(intent);
            }
        });

        binding.profitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this, ProfitReportsActivity.class);
                startActivity(intent);
            }
        });

        binding.stockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this, StockReportsActivity.class);
                startActivity(intent);
            }
        });
    }
}