package com.example.statspos.Activities.Reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.example.statspos.Adapters.Reports.Accounts.IncomeStatementAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Accounts.IncomeStatement;
import com.example.statspos.databinding.ActivityIncomeStatementBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeStatementActivity extends AppCompatActivity {

    ActivityIncomeStatementBinding binding;

    IncomeStatementAdapter incomeStatementAdapter;
    List<IncomeStatement> list;
    HP.ArrayRequest arrayRequest;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeStatementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        loadReport();
    }

    private void init(){
        binding.dateFromTB.setText(HP.getTodayDate());
        binding.dateToTB.setText(HP.getTodayDate());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        incomeStatementAdapter = new IncomeStatementAdapter(this, list);

        binding.recyclerView.setAdapter(incomeStatementAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayRequest = new HP.ArrayRequest(this, "reports/accounts/incomeStatement", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            IncomeStatement incomeStatement = gson.fromJson(response.getJSONObject(i).toString(), IncomeStatement.class);
                            list.add(incomeStatement);
                        }

                        IncomeStatement incomeStatement = list.get(0);
                        binding.sales.setText(incomeStatement.getSales());
                        binding.cgs.setText(incomeStatement.getCgs());

                        binding.grossProfit.setText(incomeStatement.getGrossProfit());
                        if(Long.valueOf(incomeStatement.getGrossProfit()) >= 0){
                            binding.grossProfitLabel.setText("Gross Profit");
                        }else {
                            binding.grossProfitLabel.setText("Gross Loss");
                        }

                        binding.totalExpenses.setText(incomeStatement.getTotalExpenses());

                        binding.netProfit.setText(HP.formatCurrency(incomeStatement.getNetProfit()));
                        if(Long.valueOf(incomeStatement.getNetProfit()) >= 0){
                            binding.netProfitLabel.setText("Net Profit");
                        }else {
                            binding.netProfitLabel.setText("Net Loss");
                        }

                    }else {
                        binding.sales.setText("0");
                        binding.cgs.setText("0");
                        binding.grossProfit.setText("0");
                        binding.totalExpenses.setText("0");
                        binding.netProfit.setText("0.00");
                    }

                    incomeStatementAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
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

        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReport();
            }
        });
    }

    private void loadReport(){
        progressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("date_from", HP.reverseDate(binding.dateFromTB.getText().toString()));
        params.put("date_to", HP.reverseDate(binding.dateToTB.getText().toString()));

        arrayRequest.request(params);
    }
}