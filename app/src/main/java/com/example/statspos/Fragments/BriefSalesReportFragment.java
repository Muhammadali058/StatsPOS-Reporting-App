package com.example.statspos.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Adapters.BriefSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.BriefSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentBriefSalesReportBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BriefSalesReportFragment extends Fragment {

    FragmentBriefSalesReportBinding binding;
    BriefSalesReportAdapter briefSalesReportAdapter;
    List<BriefSalesReport> list;
    HP.ArrayRequest arrayRequest;
    SalesReportsActivity salesReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBriefSalesReportBinding.bind(inflater.inflate(R.layout.fragment_brief_sales_report, container, false));

        init();
        loadReport();

        return binding.getRoot();
    }

    private void init(){
        salesReportsActivity = (SalesReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        briefSalesReportAdapter = new BriefSalesReportAdapter(getContext(), list);
        binding.recyclerView.setAdapter(briefSalesReportAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayRequest = new HP.ArrayRequest(getContext(), "reports/sales/briefSalesReport", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            BriefSalesReport briefSalesReport = gson.fromJson(response.getJSONObject(i).toString(), BriefSalesReport.class);
                            list.add(briefSalesReport);
                        }
                    }

                    briefSalesReportAdapter.notifyDataSetChanged();
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
        arrayRequest.request(getParams());
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
//        params.put("date_from", salesReportsActivity.getDateFrom());
//        params.put("date_to", salesReportsActivity.getDateTo());

        params.putAll(salesReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        salesReportsActivity.setRadioButtonsVisibility(false);
    }

}