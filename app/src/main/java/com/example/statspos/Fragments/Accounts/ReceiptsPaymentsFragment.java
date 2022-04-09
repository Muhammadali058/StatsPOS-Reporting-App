package com.example.statspos.Fragments.Accounts;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.AccountReportsActivity;
import com.example.statspos.Adapters.Accounts.PaymentReportAdapter;
import com.example.statspos.Adapters.Accounts.ReceiptsReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Accounts.Ledger;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentReceiptsPaymentsBinding;
import com.example.statspos.databinding.ReceiptsPaymentReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiptsPaymentsFragment extends Fragment {

    FragmentReceiptsPaymentsBinding binding;
    ReceiptsPaymentReportHelperBinding bindingInclude;

    ReceiptsReportAdapter receiptsReportAdapter;
    PaymentReportAdapter paymentReportAdapter;
    List<Ledger> list;
    HP.ObjectRequest receiptsObjectRequest;
    HP.ObjectRequest paymentsObjectRequest;
    AccountReportsActivity accountReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReceiptsPaymentsBinding.bind(inflater.inflate(R.layout.fragment_receipts_payments, container, false));
        bindingInclude = ReceiptsPaymentReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        accountReportsActivity = (AccountReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        receiptsReportAdapter = new ReceiptsReportAdapter(getContext(), list);
        paymentReportAdapter = new PaymentReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        receiptsObjectRequest = new HP.ObjectRequest(getContext(), "reports/accounts/receipts", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    bindingInclude.recyclerView.setAdapter(receiptsReportAdapter);
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));

                        for(int i = 0; i < rows.length(); i++){
                            Ledger ledger = gson.fromJson(rows.getJSONObject(i).toString(), Ledger.class);
                            list.add(ledger);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                    }

                    receiptsReportAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        paymentsObjectRequest = new HP.ObjectRequest(getContext(), "reports/accounts/payments", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    bindingInclude.recyclerView.setAdapter(paymentReportAdapter);
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));

                        for(int i = 0; i < rows.length(); i++){
                            Ledger ledger = gson.fromJson(rows.getJSONObject(i).toString(), Ledger.class);
                            list.add(ledger);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                    }

                    paymentReportAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.receiptsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReceiptsReport();
            }
        });

        binding.paymentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPaymentsReport();
            }
        });
    }

    private void loadReceiptsReport(){
        progressDialog.show();
        receiptsObjectRequest.request(getParams());
    }

    private void loadPaymentsReport(){
        progressDialog.show();
        paymentsObjectRequest.request(getParams());
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        accountReportsActivity.setDateLayoutVisibility(true);
    }
}