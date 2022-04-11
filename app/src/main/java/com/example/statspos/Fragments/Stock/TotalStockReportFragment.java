package com.example.statspos.Fragments.Stock;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.StockReportsActivity;
import com.example.statspos.Adapters.Reports.Stock.StockReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.StockReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentTotalStockReportBinding;
import com.example.statspos.databinding.StockReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalStockReportFragment extends Fragment {

    FragmentTotalStockReportBinding binding;
    StockReportHelperBinding bindingInclude;

    StockReportAdapter stockReportAdapter;
    List<StockReport> list;
    HP.ObjectRequest objectRequest;
    StockReportsActivity stockReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTotalStockReportBinding.bind(inflater.inflate(R.layout.fragment_total_stock_report, container, false));
        bindingInclude = StockReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        stockReportsActivity = (StockReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        stockReportAdapter = new StockReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(stockReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/stock/itemsStockReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();
                    Log.i("Response = ", response.toString());
                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.totalStockValue.setText(HP.formatCurrency(total.getString("totalStockValue")));

                        for(int i = 0; i < rows.length(); i++){
                            StockReport stockReport = gson.fromJson(rows.getJSONObject(i).toString(), StockReport.class);
                            list.add(stockReport);
                        }
                    }else {
                        binding.totalStockValue.setText("0.00");
                    }

                    stockReportAdapter.notifyDataSetChanged();
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

        params.putAll(stockReportsActivity.getDateParams());
        params.putAll(stockReportsActivity.getRBParams());

        return params;
    }

}