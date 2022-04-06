package com.example.statspos.Fragments.Profit;

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

import com.example.statspos.Activities.Reports.ProfitReportsActivity;
import com.example.statspos.Adapters.Profit.ItemsProfitReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Items.Items;
import com.example.statspos.Models.Reports.Profit.ItemsProfitReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentItemProfitReportBinding;
import com.example.statspos.databinding.ItemsProfitReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemProfitReportFragment extends Fragment {

    FragmentItemProfitReportBinding binding;
    ItemsProfitReportHelperBinding bindingInclude;

    ItemsProfitReportAdapter itemsProfitReportAdapter;
    List<ItemsProfitReport> list;
    HP.ArrayRequest getItemObjectRequest;
    HP.ObjectRequest objectRequest;
    ProfitReportsActivity profitReportsActivity;
    Items selectedItem = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemProfitReportBinding.bind(inflater.inflate(R.layout.fragment_item_profit_report, container, false));
        bindingInclude = ItemsProfitReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        profitReportsActivity = (ProfitReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadItems();

        list = new ArrayList<>();
        itemsProfitReportAdapter = new ItemsProfitReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(itemsProfitReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Request to getItem
        getItemObjectRequest = new HP.ArrayRequest(getContext(), "items/getItem", new HP.ArrayRequest.OnResponseHandler() {
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
        objectRequest = new HP.ObjectRequest(getContext(), "reports/profit/itemsProfitReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandProfit.setText(HP.formatCurrency(total.getString("grandProfit")));
                        binding.totalMargin.setText(HP.formatCurrency(total.getString("totalMargin")));

                        // When not sale cartons
                        if(!HP.settings.isSaleCartons()){
                            bindingInclude.crtnLabel.setVisibility(View.GONE);
                        }

                        for(int i = 0; i < rows.length(); i++){
                            ItemsProfitReport itemsProfitReport = gson.fromJson(rows.getJSONObject(i).toString(), ItemsProfitReport.class);
                            list.add(itemsProfitReport);
                        }
                    }else {
                        binding.grandProfit.setText("0.00");
                        binding.totalMargin.setText("0.00%");

                        // When not sale cartons
                        if(!HP.settings.isSaleCartons()){
                            bindingInclude.crtnLabel.setVisibility(View.GONE);
                        }
                    }

                    itemsProfitReportAdapter.notifyDataSetChanged();
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
                    getItemObjectRequest.request(params);
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
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "items/searchItem", new HP.ObjectRequest.OnResponseHandler() {
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
        params2.put("text", "");
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
        params.put("id", selectedItem.getId());

        params.putAll(profitReportsActivity.getDateParams());
        params.putAll(profitReportsActivity.getRBParams());

        return params;
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        profitReportsActivity.setRadioButtonsVisibility(true);
    }

}