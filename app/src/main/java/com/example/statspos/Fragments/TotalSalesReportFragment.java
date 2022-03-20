package com.example.statspos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.statspos.Adapters.TotalSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.TotalSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentTotalSalesReportBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TotalSalesReportFragment extends Fragment {

    FragmentTotalSalesReportBinding binding;
    TotalSalesReportAdapter totalSalesReportAdapter;
    List<TotalSalesReport> list;
    HP.ObjectRequest objectRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTotalSalesReportBinding.bind(inflater.inflate(R.layout.fragment_total_sales_report, container, false));

        init();
        loadReport();

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

        binding.typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                loadReport();
            }
        });

        return binding.getRoot();
    }

    private void init(){
        list = new ArrayList<>();
        totalSalesReportAdapter = new TotalSalesReportAdapter(getContext(), list);
        binding.recyclerView.setAdapter(totalSalesReportAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/sales/totalSalesReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                list.clear();
                Gson gson = new Gson();

                try {
                    binding.grandTotal.setText(HP.formatCurrency(response.getJSONObject("total").getString("grandTotal")));

                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        TotalSalesReport totalSalesReport = gson.fromJson(jsonArray.getJSONObject(i).toString(), TotalSalesReport.class);
                        list.add(totalSalesReport);
                    }

                    totalSalesReportAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadReport(){
        objectRequest.request(getParams());
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("date_from", binding.dateFromTB.getText().toString());
        params.put("date_to", binding.dateToTB.getText().toString());

        if(binding.typeRetailRB.isChecked()){
            params.put("is_retail", "1");
        }else if(binding.typeWholesaleRB.isChecked()){
            params.put("is_retail", "0");
        }

        return params;
    }
}