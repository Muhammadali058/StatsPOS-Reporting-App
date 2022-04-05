package com.example.statspos.Fragments.Purchase;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.PurchaseReportsActivity;
import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Adapters.Purchase.BriefPurchaseReportAdapter;
import com.example.statspos.Adapters.Sales.BriefSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Purchase.BriefPurchaseReport;
import com.example.statspos.Models.Reports.Sales.BriefSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentBriefPurchaseReportBinding;
import com.example.statspos.databinding.FragmentBriefSalesReportBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BriefPurchaseReportFragment extends Fragment {

    FragmentBriefPurchaseReportBinding binding;
    BriefPurchaseReportAdapter briefPurchaseReportAdapter;
    List<BriefPurchaseReport> list;
    HP.ArrayRequest arrayRequest;
    PurchaseReportsActivity purchaseReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBriefPurchaseReportBinding.bind(inflater.inflate(R.layout.fragment_brief_purchase_report, container, false));

        init();
        loadReport();

        return binding.getRoot();
    }

    private void init(){
        purchaseReportsActivity = (PurchaseReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        briefPurchaseReportAdapter = new BriefPurchaseReportAdapter(getContext(), list);
        binding.recyclerView.setAdapter(briefPurchaseReportAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayRequest = new HP.ArrayRequest(getContext(), "reports/purchase/briefPurchaseReport", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            BriefPurchaseReport briefPurchaseReport = gson.fromJson(response.getJSONObject(i).toString(), BriefPurchaseReport.class);
                            list.add(briefPurchaseReport);
                        }
                    }

                    briefPurchaseReportAdapter.notifyDataSetChanged();
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

        params.putAll(purchaseReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        purchaseReportsActivity.setRadioButtonsVisibility(false);
    }

}