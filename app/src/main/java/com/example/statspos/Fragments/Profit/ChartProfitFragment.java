package com.example.statspos.Fragments.Profit;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.ProfitReportsActivity;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.ChartReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentChartProfitBinding;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartProfitFragment extends Fragment {

    FragmentChartProfitBinding binding;
    HP.ArrayRequest chartDailyArrayRequest;
    HP.ArrayRequest chartMonthlyArrayRequest;
    HP.ArrayRequest chartYearlyArrayRequest;
    ProfitReportsActivity profitReportsActivity;
    ProgressDialog progressDialog;

    ArrayList<BarEntry> barEntries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartProfitBinding.bind(inflater.inflate(R.layout.fragment_chart_profit, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){
        profitReportsActivity = (ProfitReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        barEntries = new ArrayList<>();
        requests();

        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReport();
            }
        });

    }

    private void requests(){
        chartDailyArrayRequest = new HP.ArrayRequest(getContext(), "reports/profit/chartDaily", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    barEntries.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            ChartReport chartReport = gson.fromJson(response.getJSONObject(i).toString(), ChartReport.class);
                            barEntries.add(new BarEntry(i+1, Float.valueOf(chartReport.getTotal())));
                        }
                    }

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Chart Daily");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);

                    BarData barData = new BarData(barDataSet);

                    binding.barChart.setFitBars(true);
                    binding.barChart.setData(barData);
                    binding.barChart.getDescription().setText("Bar Chart");
                    binding.barChart.animateY(1000);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        chartMonthlyArrayRequest = new HP.ArrayRequest(getContext(), "reports/profit/chartMonthly", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    barEntries.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            ChartReport chartReport = gson.fromJson(response.getJSONObject(i).toString(), ChartReport.class);
                            barEntries.add(new BarEntry(i+1, Float.valueOf(chartReport.getTotal())));
                        }
                    }

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Chart Monthly");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);

                    BarData barData = new BarData(barDataSet);

                    binding.barChart.setFitBars(true);
                    binding.barChart.setData(barData);
                    binding.barChart.getDescription().setText("Bar Chart");
                    binding.barChart.animateY(1000);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        chartYearlyArrayRequest = new HP.ArrayRequest(getContext(), "reports/profit/chartYearly", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    barEntries.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            ChartReport chartReport = gson.fromJson(response.getJSONObject(i).toString(), ChartReport.class);
                            barEntries.add(new BarEntry(i+1, Float.valueOf(chartReport.getTotal())));
                        }
                    }

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Chart Yearly");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);

                    BarData barData = new BarData(barDataSet);

                    binding.barChart.setFitBars(true);
                    binding.barChart.setData(barData);
                    binding.barChart.getDescription().setText("Bar Chart");
                    binding.barChart.animateY(1000);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadReport(){
        progressDialog.show();
        if(binding.chartDailyRB.isChecked()){
            chartDailyArrayRequest.request(getParams());
        }else if(binding.chartMonthlyRB.isChecked()){
            chartMonthlyArrayRequest.request(getParams());
        }if(binding.chartYearlyRB.isChecked()){
            chartYearlyArrayRequest.request(getParams());
        }
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