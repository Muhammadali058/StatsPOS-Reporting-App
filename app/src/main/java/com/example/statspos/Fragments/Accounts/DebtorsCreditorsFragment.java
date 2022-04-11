package com.example.statspos.Fragments.Accounts;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.AccountReportsActivity;
import com.example.statspos.Adapters.Reports.Accounts.DebtorsCreditorsAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Accounts.DebtorsCreditors;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentDebtorsCreditorsBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebtorsCreditorsFragment extends Fragment {

    FragmentDebtorsCreditorsBinding binding;

    DebtorsCreditorsAdapter debtorsCreditorsAdapter;
    List<DebtorsCreditors> list;
    HP.ObjectRequest debtorsObjectRequest;
    HP.ObjectRequest creditorsObjectRequest;
    AccountReportsActivity accountReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDebtorsCreditorsBinding.bind(inflater.inflate(R.layout.fragment_debtors_creditors, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){
        binding.dateTB.setText(HP.getTodayDate());

        accountReportsActivity = (AccountReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        debtorsCreditorsAdapter = new DebtorsCreditorsAdapter(getContext(), list);

        binding.recyclerView.setAdapter(debtorsCreditorsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        debtorsObjectRequest = new HP.ObjectRequest(getContext(), "reports/accounts/debtors", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));

                        for(int i = 0; i < rows.length(); i++){
                            DebtorsCreditors debtorsCreditors = gson.fromJson(rows.getJSONObject(i).toString(), DebtorsCreditors.class);
                            list.add(debtorsCreditors);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                    }

                    debtorsCreditorsAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        creditorsObjectRequest = new HP.ObjectRequest(getContext(), "reports/accounts/creditors", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));

                        for(int i = 0; i < rows.length(); i++){
                            DebtorsCreditors debtorsCreditors = gson.fromJson(rows.getJSONObject(i).toString(), DebtorsCreditors.class);
                            list.add(debtorsCreditors);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                    }

                    debtorsCreditorsAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        binding.dateTB.setOnClickListener(new HP.OnDateClickListener(getContext(), new HP.OnDateClickListener.OnDateSet() {
            @Override
            public void onDateSet(String date) {
                binding.dateTB.setText(date);
            }
        }));

        binding.debtorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDebtorsReport();
            }
        });

        binding.creditorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCreditorsReport();
            }
        });
    }

    private void loadDebtorsReport(){
        progressDialog.show();
        debtorsObjectRequest.request(getParams());
    }

    private void loadCreditorsReport(){
        progressDialog.show();
        creditorsObjectRequest.request(getParams());
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("date", HP.reverseDate(binding.dateTB.getText().toString()));

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        accountReportsActivity.setDateLayoutVisibility(false);
    }
}