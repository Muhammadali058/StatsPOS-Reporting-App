package com.example.statspos.Fragments.Profit;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.ProfitReportsActivity;
import com.example.statspos.Adapters.Reports.Profit.TotalProfitReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Profit.TotalProfitReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentTotalProfitReportBinding;
import com.example.statspos.databinding.TotalProfitReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalProfitReportFragment extends Fragment {

    FragmentTotalProfitReportBinding binding;
    TotalProfitReportHelperBinding bindingInclude;

    TotalProfitReportAdapter totalProfitReportAdapter;
    List<TotalProfitReport> list;
    HP.ObjectRequest objectRequest;
    ProfitReportsActivity profitReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTotalProfitReportBinding.bind(inflater.inflate(R.layout.fragment_total_profit_report, container, false));
        bindingInclude = TotalProfitReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        profitReportsActivity = (ProfitReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        totalProfitReportAdapter = new TotalProfitReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(totalProfitReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/profit/totalProfitReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandProfit.setText(HP.formatCurrency(total.getString("grandProfit")));
                        binding.totalMargin.setText(HP.formatCurrency(total.getString("totalMargin")));

                        for(int i = 0; i < rows.length(); i++){
                            TotalProfitReport totalProfitReport = gson.fromJson(rows.getJSONObject(i).toString(), TotalProfitReport.class);
                            list.add(totalProfitReport);
                        }
                    }else {
                        binding.grandProfit.setText("0.00");
                        binding.totalMargin.setText("0.00%");
                    }

                    totalProfitReportAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReport();
            }
        });
    }

    private void loadReport(){
        progressDialog.show();
        objectRequest.request(getParams());
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();

        params.putAll(profitReportsActivity.getDateParams());
        params.putAll(profitReportsActivity.getRBParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        profitReportsActivity.setRadioButtonsVisibility(true);
    }

}