package com.example.statspos.Fragments.Purchase;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.PurchaseReportsActivity;
import com.example.statspos.Adapters.Reports.Purchase.TotalPurchaseReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Purchase.TotalPurchaseReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentTotalPurchaseReportBinding;
import com.example.statspos.databinding.TotalPurchaseReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalPurchaseReportFragment extends Fragment {

    FragmentTotalPurchaseReportBinding binding;
    TotalPurchaseReportHelperBinding bindingInclude;

    TotalPurchaseReportAdapter totalPurchaseReportAdapter;
    List<TotalPurchaseReport> list;
    HP.ObjectRequest objectRequest;
    PurchaseReportsActivity purchaseReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTotalPurchaseReportBinding.bind(inflater.inflate(R.layout.fragment_total_purchase_report, container, false));
        bindingInclude = TotalPurchaseReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        purchaseReportsActivity = (PurchaseReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        totalPurchaseReportAdapter = new TotalPurchaseReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(totalPurchaseReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/purchase/totalPurchaseReport", new HP.ObjectRequest.OnResponseHandler() {
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
                            TotalPurchaseReport totalPurchaseReport = gson.fromJson(rows.getJSONObject(i).toString(), TotalPurchaseReport.class);
                            list.add(totalPurchaseReport);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                        binding.totalBills.setText("0");
                    }

                    totalPurchaseReportAdapter.notifyDataSetChanged();
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

        params.putAll(purchaseReportsActivity.getDateParams());
        params.putAll(purchaseReportsActivity.getRBParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        purchaseReportsActivity.setRadioButtonsVisibility(true);
    }

}