package com.example.statspos.Fragments.Accounts;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.statspos.Activities.Reports.AccountReportsActivity;
import com.example.statspos.Adapters.Accounts.CashAccountAdapter;
import com.example.statspos.Adapters.Accounts.LedgerReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Accounts.Ledger;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentCashAccountBinding;
import com.example.statspos.databinding.LedgerReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashAccountFragment extends Fragment {

    FragmentCashAccountBinding binding;

    CashAccountAdapter cashAccountAdapter;
    List<Ledger> list;
    HP.ArrayRequest arrayRequest;
    AccountReportsActivity accountReportsActivity;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCashAccountBinding.bind(inflater.inflate(R.layout.fragment_cash_account, container, false));

        init();

        return binding.getRoot();
    }

    private void init(){
        accountReportsActivity = (AccountReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        list = new ArrayList<>();
        cashAccountAdapter = new CashAccountAdapter(getContext(), list);

        binding.recyclerView.setAdapter(cashAccountAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayRequest = new HP.ArrayRequest(getContext(), "reports/accounts/cashAccount", new HP.ArrayRequest.OnResponseHandler() {
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

                        binding.newBalance.setText(HP.formatCurrency(String.valueOf(newBalance))  + " " + drOrCr);

                        long oldBalance = Long.valueOf(ledger.getOldBalance());
                        if(oldBalance > 0) {
                            binding.oldDebit.setText(String.valueOf(oldBalance));
                            binding.oldCredit.setText("0");
                        }
                        else if(oldBalance == 0) {
                            binding.oldDebit.setText("0");
                            binding.oldCredit.setText("0");
                        }
                        else {
                            binding.oldDebit.setText("0");
                            binding.oldCredit.setText(String.valueOf(oldBalance));
                        }

                        binding.oldBalanceLayout.setVisibility(View.VISIBLE);
                    }else {
                        binding.newBalance.setText("0.00");
                        binding.oldBalanceLayout.setVisibility(View.GONE);
                    }

                    cashAccountAdapter.notifyDataSetChanged();
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

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        accountReportsActivity.setDateLayoutVisibility(true);
    }
}