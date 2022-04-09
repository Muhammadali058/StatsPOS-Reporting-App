package com.example.statspos.Fragments.Accounts;

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

import com.example.statspos.Activities.Reports.AccountReportsActivity;
import com.example.statspos.Adapters.Accounts.LedgerReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Accounts.Customers;
import com.example.statspos.Models.Reports.Accounts.Ledger;
import com.example.statspos.Models.Accounts.Vendors;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentLedgerReportBinding;
import com.example.statspos.databinding.LedgerReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LedgerReportFragment extends Fragment {

    FragmentLedgerReportBinding binding;
    LedgerReportHelperBinding bindingInclude;

    LedgerReportAdapter ledgerReportAdapter;
    List<Ledger> list;
    HP.ArrayRequest arrayRequest;
    AccountReportsActivity accountReportsActivity;
    Customers selectedCustomer = null;
    Vendors selectedVendor = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLedgerReportBinding.bind(inflater.inflate(R.layout.fragment_ledger_report, container, false));
        bindingInclude = LedgerReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        accountReportsActivity = (AccountReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadCustomers();
        loadVendors();

        list = new ArrayList<>();
        ledgerReportAdapter = new LedgerReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(ledgerReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayRequest = new HP.ArrayRequest(getContext(), "reports/accounts/ledger", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            Ledger ledger = gson.fromJson(response.getJSONObject(i).toString(), Ledger.class);
                            list.add(ledger);
                        }

                        Ledger ledger = list.get(0);
                        long newBalance = Long.valueOf(ledger.getNewBalance());
                        String drOrCr = "";
                        if(newBalance < 0)
                            drOrCr = "Cr";
                        else if (newBalance == 0)
                            drOrCr = "";
                        else
                            drOrCr = "Dr";

                        binding.newBalance.setText(HP.formatCurrency(String.valueOf(Math.abs(newBalance)))  + " " + drOrCr);

                        long oldBalance = Long.valueOf(ledger.getOldBalance());
                        if(oldBalance > 0){
                            bindingInclude.oldDebit.setText(String.valueOf(oldBalance));
                            bindingInclude.oldCredit.setText("0");
                        }
                        else if(oldBalance == 0) {
                            bindingInclude.oldDebit.setText("0");
                            bindingInclude.oldCredit.setText("0");
                        }
                        else {
                            bindingInclude.oldDebit.setText("0");
                            bindingInclude.oldCredit.setText(String.valueOf(Math.abs(oldBalance)));
                        }

                        bindingInclude.oldBalanceLayout.setVisibility(View.VISIBLE);
                    }else {
                        binding.newBalance.setText("0.00");
                        bindingInclude.oldBalanceLayout.setVisibility(View.GONE);
                    }

                    ledgerReportAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Customer
        binding.customerRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCustomerReport();
            }
        });

        binding.customerDropdownBtn.setOnClickListener(new View.OnClickListener() {
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
                loadCustomerReport();
            }
        });

        // Vendor
        binding.vendorRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadVendorReport();
            }
        });

        binding.vendorDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.vendorTB.showDropDown();
            }
        });

        binding.vendorTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.vendorTB.showDropDown();
                }
            }
        });

        binding.vendorTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedVendor = (Vendors) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                loadVendorReport();
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

    private void loadVendors(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "accounts/searchVendor", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Vendors> vendorsList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        Vendors vendor = gson.fromJson(jsonArray.getJSONObject(i).toString(), Vendors.class);
                        vendorsList.add(vendor);
                    }

                    ArrayAdapter<Vendors> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, vendorsList);
                    binding.vendorTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("text", "");
        objectRequest.request(params2);
    }

    private void loadCustomerReport(){
        if(selectedCustomer != null) {
            progressDialog.show();
            arrayRequest.request(getParamsCustomer());
        }
    }

    private void loadVendorReport(){
        if(selectedVendor != null) {
            progressDialog.show();
            arrayRequest.request(getParamsVendor());
        }
    }

    private Map<String, String> getParamsCustomer(){
        Map<String, String> params = new HashMap<>();
        params.put("account_id", selectedCustomer.getId());

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    private Map<String, String> getParamsVendor(){
        Map<String, String> params = new HashMap<>();
        params.put("account_id", selectedVendor.getId());

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        accountReportsActivity.setDateLayoutVisibility(true);
    }
}