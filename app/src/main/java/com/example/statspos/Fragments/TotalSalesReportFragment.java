package com.example.statspos.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Adapters.TotalSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.TotalSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentTotalSalesReportBinding;
import com.example.statspos.databinding.TotalSalesReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalSalesReportFragment extends Fragment {

    FragmentTotalSalesReportBinding binding;
    TotalSalesReportHelperBinding bindingInclude;

    TotalSalesReportAdapter totalSalesReportAdapter;
    List<TotalSalesReport> list;
    HP.ObjectRequest objectRequest;
    SalesReportsActivity salesReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTotalSalesReportBinding.bind(inflater.inflate(R.layout.fragment_total_sales_report, container, false));
        bindingInclude = TotalSalesReportHelperBinding.bind(binding.getRoot());

        init();
//        loadReport();

        return binding.getRoot();
    }

    private void init(){
        salesReportsActivity = (SalesReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        totalSalesReportAdapter = new TotalSalesReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(totalSalesReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/sales/totalSalesReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));
                        binding.totalBills.setText(total.getString("totalRows"));

                        for(int i = 0; i < rows.length(); i++){
                            TotalSalesReport totalSalesReport = gson.fromJson(rows.getJSONObject(i).toString(), TotalSalesReport.class);
                            list.add(totalSalesReport);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                        binding.totalBills.setText("0");
                    }

                    totalSalesReportAdapter.notifyDataSetChanged();
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
//        params.put("date_from", salesReportsActivity.getDateFrom());
//        params.put("date_to", salesReportsActivity.getDateTo());

        params.putAll(salesReportsActivity.getDateParams());
        params.putAll(salesReportsActivity.getRBParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        salesReportsActivity.setRadioButtonsVisibility(true);
//        loadReport();
    }

}