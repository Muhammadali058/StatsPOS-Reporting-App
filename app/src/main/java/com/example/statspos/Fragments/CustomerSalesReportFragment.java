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
import com.example.statspos.Adapters.TotalSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Accounts.Customers;
import com.example.statspos.Models.Reports.TotalSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentByCustomerSalesReportBinding;
import com.example.statspos.databinding.TotalSalesReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerSalesReportFragment extends Fragment {

    FragmentByCustomerSalesReportBinding binding;
    TotalSalesReportHelperBinding bindingInclude;

    TotalSalesReportAdapter totalSalesReportAdapter;
    List<TotalSalesReport> list;
    HP.ObjectRequest objectRequest;
    SalesReportsActivity salesReportsActivity;
    Customers selectedCustomer = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentByCustomerSalesReportBinding.bind(inflater.inflate(R.layout.fragment_by_customer_sales_report, container, false));
        bindingInclude = TotalSalesReportHelperBinding.bind(binding.getRoot());

        init();
//        loadReport();

        return binding.getRoot();
    }

    private void init(){
        salesReportsActivity = (SalesReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadCustomers();

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

        binding.customerInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.customerTB.showDropDown();
            }
        });

        binding.customerTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.customerTB.showDropDown();
                }
            }
        });

        binding.customerTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCustomer = (Customers) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                loadReport();
            }
        });
    }

    private void loadCustomers(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "accounts/searchCustomer", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Customers> customersList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        Customers customer = gson.fromJson(jsonArray.getJSONObject(i).toString(), Customers.class);
                        customersList.add(customer);
                    }

                    ArrayAdapter<Customers> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, customersList);
                    binding.customerTB.setAdapter(arrayAdapter);


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
        if(selectedCustomer != null) {
            progressDialog.show();
            objectRequest.request(getParams());
        }
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("date_from", salesReportsActivity.getDateFrom());
        params.put("date_to", salesReportsActivity.getDateTo());
        params.put("customer_id", selectedCustomer.getId());

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