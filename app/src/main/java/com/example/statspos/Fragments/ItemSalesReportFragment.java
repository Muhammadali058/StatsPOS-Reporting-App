package com.example.statspos.Fragments;

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

import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Adapters.ItemsSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Items.Items;
import com.example.statspos.Models.Reports.ItemsSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentByItemSalesReportBinding;
import com.example.statspos.databinding.ItemsSalesReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSalesReportFragment extends Fragment {

    FragmentByItemSalesReportBinding binding;
    ItemsSalesReportHelperBinding bindingInclude;

    ItemsSalesReportAdapter itemsSalesReportAdapter;
    List<ItemsSalesReport> list;
    HP.ObjectRequest objectRequest;
    SalesReportsActivity salesReportsActivity;
    Items selectedItem = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentByItemSalesReportBinding.bind(inflater.inflate(R.layout.fragment_by_item_sales_report, container, false));
        bindingInclude = ItemsSalesReportHelperBinding.bind(binding.getRoot());

        init();
//        loadReport();

        return binding.getRoot();
    }

    private void init(){
        salesReportsActivity = (SalesReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadItems();

        list = new ArrayList<>();
        itemsSalesReportAdapter = new ItemsSalesReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(itemsSalesReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/sales/itemsSalesReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));

                        // When not sale cartons
                        if(!HP.settings.isSaleCartons()){
                            bindingInclude.crtnLabel.setVisibility(View.GONE);
                            bindingInclude.crtnRateLabel.setVisibility(View.GONE);

                            String totalQty = "Pcs = " + total.getString("totalQty");
                            binding.totalQtyLabel.setText(totalQty);
                        }else { // When sale cartons
                            String totalQty = "Pcs = " + total.getString("totalQty") + ", Crtn = " + total.getString("totalCrtn");
                            binding.totalQtyLabel.setText(totalQty);
                        }

                        for(int i = 0; i < rows.length(); i++){
                            ItemsSalesReport itemsSalesReport = gson.fromJson(rows.getJSONObject(i).toString(), ItemsSalesReport.class);
                            list.add(itemsSalesReport);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");

                        // When not sale cartons
                        if(!HP.settings.isSaleCartons()){
                            binding.totalQtyLabel.setText("Pcs = 0");
                        }else {
                            binding.totalQtyLabel.setText("Pcs = 0, Crtn = 0");
                        }
                    }

                    itemsSalesReportAdapter.notifyDataSetChanged();
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

        binding.itemnameInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
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
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
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
        params.put("date_from", salesReportsActivity.getDateFrom());
        params.put("date_to", salesReportsActivity.getDateTo());
        params.put("report_by", "item");
        params.put("id", selectedItem.getId());

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