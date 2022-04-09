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
import com.example.statspos.Models.Accounts.Banks;
import com.example.statspos.Models.Reports.Accounts.Ledger;
import com.example.statspos.Models.Accounts.SubBanks;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentBankStatementBinding;
import com.example.statspos.databinding.LedgerReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankStatementFragment extends Fragment {

    FragmentBankStatementBinding binding;
    LedgerReportHelperBinding bindingInclude;

    LedgerReportAdapter ledgerReportAdapter;
    List<Ledger> list;
    HP.ArrayRequest arrayRequest;
    AccountReportsActivity accountReportsActivity;
    Banks selectedBank = null;
    SubBanks selectedSubBank = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBankStatementBinding.bind(inflater.inflate(R.layout.fragment_bank_statement, container, false));
        bindingInclude = LedgerReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        accountReportsActivity = (AccountReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadBanks();

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

                        binding.newBalance.setText(HP.formatCurrency(String.valueOf(newBalance))  + " " + drOrCr);

                        long oldBalance = Long.valueOf(ledger.getOldBalance());
                        if(oldBalance > 0)
                            bindingInclude.oldDebit.setText(String.valueOf(oldBalance));
                        else if(oldBalance == 0) {
                            bindingInclude.oldDebit.setText("0");
                            bindingInclude.oldCredit.setText("0");
                        }
                        else
                            bindingInclude.oldCredit.setText(String.valueOf(oldBalance));

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

        binding.bankDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bankTB.showDropDown();
            }
        });

        binding.bankTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.bankTB.showDropDown();
                }
            }
        });

        binding.bankTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBank = (Banks) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                binding.subBankTB.setText("");
                selectedSubBank = null;
                loadSubBanks();

                binding.subBankTB.requestFocus();
            }
        });

        // Sub-Banks
        binding.subBankRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadReport();
            }
        });

        binding.subBankDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.subBankTB.showDropDown();
            }
        });

        binding.subBankTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.subBankTB.showDropDown();
                }
            }
        });

        binding.subBankTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubBank = (SubBanks) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                loadReport();
            }
        });
    }

    private void loadBanks(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "accounts/searchBank", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Banks> banksList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        Banks bank = gson.fromJson(jsonArray.getJSONObject(i).toString(), Banks.class);
                        banksList.add(bank);
                    }

                    ArrayAdapter<Banks> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, banksList);
                    binding.bankTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("text", "");
        objectRequest.request(params2);
    }

    private void loadSubBanks(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "accounts/searchSubBank", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<SubBanks> subBanksList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        SubBanks subBanks = gson.fromJson(jsonArray.getJSONObject(i).toString(), SubBanks.class);
                        subBanksList.add(subBanks);
                    }

                    ArrayAdapter<SubBanks> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, subBanksList);
                    binding.subBankTB.setAdapter(arrayAdapter);

                    binding.subBankTB.showDropDown();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("bank_id", selectedBank.getId());
        params.put("text", "");

        if(selectedBank != null){
            objectRequest.request(params);
        }
    }

    private void loadReport(){
        if(selectedSubBank != null) {
            progressDialog.show();
            arrayRequest.request(getParams());
        }
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("account_id", selectedSubBank.getId());

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        accountReportsActivity.setDateLayoutVisibility(true);
    }
}