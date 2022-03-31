package com.example.statspos.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Adapters.TotalSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Accounts.Customers;
import com.example.statspos.Models.Reports.BriefSalesReport;
import com.example.statspos.Models.Reports.ChartSalesReport;
import com.example.statspos.Models.Reports.TotalSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentChartSalesBinding;
import com.example.statspos.databinding.TotalSalesReportHelperBinding;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartSalesFragment extends Fragment {

    FragmentChartSalesBinding binding;
    HP.ArrayRequest chartDailyArrayRequest;
    SalesReportsActivity salesReportsActivity;
    ProgressDialog progressDialog;

    ArrayList<BarEntry> barEntries;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChartSalesBinding.bind(inflater.inflate(R.layout.fragment_chart_sales, container, false));

        init();
        loadReport();

        return binding.getRoot();
    }

    private void init(){
        salesReportsActivity = (SalesReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        barEntries = new ArrayList<>();

        chartDailyArrayRequest = new HP.ArrayRequest(getContext(), "reports/sales/chartDaily", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    barEntries.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            ChartSalesReport chartSalesReport = gson.fromJson(response.getJSONObject(i).toString(), ChartSalesReport.class);

                            barEntries.add(new BarEntry(i+1, Float.valueOf(chartSalesReport.getTotal())));
                        }
                    }

                    BarDataSet barDataSet = new BarDataSet(barEntries, "Chart Daily");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    barDataSet.setValueTextSize(16f);

                    BarData barData = new BarData(barDataSet);

                    binding.barChart.setFitBars(true);
                    binding.barChart.setData(barData);
                    binding.barChart.getDescription().setText("Bar Chart Example");
                    binding.barChart.animateY(2000);

//                    binding.barChart.notifyDataSetChanged();
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
            chartDailyArrayRequest.request(getParams());
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("date_from", salesReportsActivity.getDateFrom());
        params.put("date_to", salesReportsActivity.getDateTo());

        params.putAll(salesReportsActivity.getRBParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        salesReportsActivity.setRadioButtonsVisivility(true);
//        loadReport();
    }

}