package com.example.statspos.Fragments.Profit;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.ProfitReportsActivity;
import com.example.statspos.Adapters.Profit.BriefProfitReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Profit.BriefProfitReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentBriefProfitReportBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BriefProfitReportFragment extends Fragment {

    FragmentBriefProfitReportBinding binding;
    BriefProfitReportAdapter briefProfitReportAdapter;
    List<BriefProfitReport> list;
    HP.ArrayRequest arrayRequest;
    ProfitReportsActivity profitReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBriefProfitReportBinding.bind(inflater.inflate(R.layout.fragment_brief_profit_report, container, false));

        init();
        loadReport();

        return binding.getRoot();
    }

    private void init(){
        profitReportsActivity = (ProfitReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        briefProfitReportAdapter = new BriefProfitReportAdapter(getContext(), list);
        binding.recyclerView.setAdapter(briefProfitReportAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayRequest = new HP.ArrayRequest(getContext(), "reports/profit/briefProfitReport", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            BriefProfitReport briefProfitReport = gson.fromJson(response.getJSONObject(i).toString(), BriefProfitReport.class);
                            list.add(briefProfitReport);
                        }
                    }

                    briefProfitReportAdapter.notifyDataSetChanged();
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

        params.putAll(profitReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        profitReportsActivity.setRadioButtonsVisibility(false);
    }

}