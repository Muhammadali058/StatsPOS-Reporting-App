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
import com.example.statspos.Adapters.Accounts.PaymentReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Reports.Accounts.Ledger;
import com.example.statspos.Models.Accounts.Expenses;
import com.example.statspos.Models.Accounts.SubExpenses;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentExpenseReportBinding;
import com.example.statspos.databinding.ReceiptsPaymentReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseReportFragment extends Fragment {

    FragmentExpenseReportBinding binding;
    ReceiptsPaymentReportHelperBinding bindingInclude;

    PaymentReportAdapter paymentReportAdapter;
    List<Ledger> list;
    HP.ObjectRequest objectRequest;
    AccountReportsActivity accountReportsActivity;
    Expenses selectedExpense = null;
    SubExpenses selectedSubExpense = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExpenseReportBinding.bind(inflater.inflate(R.layout.fragment_expense_report, container, false));
        bindingInclude = ReceiptsPaymentReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        accountReportsActivity = (AccountReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadExpenses();

        list = new ArrayList<>();
        paymentReportAdapter = new PaymentReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(paymentReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/accounts/expenses", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("totalDebit")));

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

        // Total Expenses
        binding.totalExpenseReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTotalExpenseReport();
            }
        });

        // Expenses
        binding.expenseRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadExpenseReport();
            }
        });

        binding.expenseDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.expenseTB.showDropDown();
            }
        });

        binding.expenseTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.expenseTB.showDropDown();
                }
            }
        });

        binding.expenseTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedExpense = (Expenses) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                binding.subExpenseTB.setText("");
                selectedSubExpense = null;
                loadSubExpenses();
                loadExpenseReport();
            }
        });

        // Sub-Expenses
        binding.subExpenseRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubExpenseReport();
            }
        });

        binding.subExpenseDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.subExpenseTB.showDropDown();
            }
        });

        binding.subExpenseTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.subExpenseTB.showDropDown();
                }
            }
        });

        binding.subExpenseTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubExpense = (SubExpenses) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                loadSubExpenseReport();
            }
        });
    }

    private void loadExpenses(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "accounts/searchExpense", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Expenses> expensesList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        Expenses expenses = gson.fromJson(jsonArray.getJSONObject(i).toString(), Expenses.class);
                        expensesList.add(expenses);
                    }

                    ArrayAdapter<Expenses> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, expensesList);
                    binding.expenseTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("text", "");
        objectRequest.request(params2);
    }

    private void loadSubExpenses(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "accounts/searchSubExpense", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<SubExpenses> subExpensesList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        SubExpenses subExpenses = gson.fromJson(jsonArray.getJSONObject(i).toString(), SubExpenses.class);
                        subExpensesList.add(subExpenses);
                    }

                    ArrayAdapter<SubExpenses> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, subExpensesList);
                    binding.subExpenseTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("expense_id", selectedExpense.getId());
        params.put("text", "");

        if(selectedExpense != null){
            objectRequest.request(params);
        }
    }

    private void loadTotalExpenseReport(){
        progressDialog.show();
        objectRequest.request(getParams());
    }

    private void loadExpenseReport(){
        if(selectedExpense != null) {
            progressDialog.show();
            objectRequest.request(getParamsExpenses());
        }
    }

    private void loadSubExpenseReport(){
        if(selectedSubExpense != null) {
            progressDialog.show();
            objectRequest.request(getParamsSubExpenses());
        }
    }

    private Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    private Map<String, String> getParamsExpenses(){
        Map<String, String> params = new HashMap<>();
        params.put("expense_id", selectedExpense.getId());

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    private Map<String, String> getParamsSubExpenses(){
        Map<String, String> params = new HashMap<>();
        params.put("sub_expense_id", selectedSubExpense.getId());

        params.putAll(accountReportsActivity.getDateParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        accountReportsActivity.setDateLayoutVisibility(true);
    }
}