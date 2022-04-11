package com.example.statspos.Fragments.Stock;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.statspos.Activities.Reports.StockReportsActivity;
import com.example.statspos.Adapters.Reports.Stock.StockReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Items.Items;
import com.example.statspos.Models.Reports.StockReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentItemStockReportBinding;
import com.example.statspos.databinding.StockReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStockReportFragment extends Fragment {

    FragmentItemStockReportBinding binding;
    StockReportHelperBinding bindingInclude;

    StockReportAdapter stockReportAdapter;
    List<StockReport> list;
    HP.ArrayRequest getItemArrayRequest;
    HP.ObjectRequest objectRequest;
    StockReportsActivity stockReportsActivity;
    Items selectedItem = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemStockReportBinding.bind(inflater.inflate(R.layout.fragment_item_stock_report, container, false));
        bindingInclude = StockReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        stockReportsActivity = (StockReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadItems();

        list = new ArrayList<>();
        stockReportAdapter = new StockReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(stockReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Request to getItem
        getItemArrayRequest = new HP.ArrayRequest(getContext(), "items/getItem", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length() > 0){
                        Gson gson = new Gson();
                        selectedItem = gson.fromJson(response.getJSONObject(0).toString(), Items.class);
                        binding.itemnameTB.setText(selectedItem.getItemname());
                        hideKeyboard();
                        loadReport();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Report Request
        objectRequest = new HP.ObjectRequest(getContext(), "reports/stock/itemsStockReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

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

        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = binding.itemnameTB.getText().toString();
                if(text != "") {
                    Map<String, String> params = new HashMap<>();
                    params.put("text", text);
                    getItemArrayRequest.request(params);
                }
            }
        });

        binding.refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReport();
            }
        });

        binding.dropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.itemnameTB.showDropDown();
            }
        });

        binding.itemnameTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.itemnameTB.showDropDown();
            }
        });

        binding.itemnameTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.itemnameTB.showDropDown();
                }
            }
        });

        binding.itemnameTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (Items) adapterView.getItemAtPosition(i);
                hideKeyboard();
                loadReport();
            }
        });
    }

    private void loadItems(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "items/loadItems", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Items> itemsList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        Items item = gson.fromJson(jsonArray.getJSONObject(i).toString(), Items.class);
                        itemsList.add(item);
                    }

                    ArrayAdapter<Items> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, itemsList);
                    binding.itemnameTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        objectRequest.request(params2);
    }

    private void loadReport(){
        if(selectedItem != null) {
            progressDialog.show();
            objectRequest.request(getParams());
        }
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("report_by", "item");
        params.put("id", String.valueOf(selectedItem.getId()));

        params.putAll(stockReportsActivity.getDateParams());
        params.putAll(stockReportsActivity.getRBParams());

        return params;
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

}