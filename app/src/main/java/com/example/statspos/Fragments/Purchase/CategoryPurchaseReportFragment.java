package com.example.statspos.Fragments.Purchase;

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

import com.example.statspos.Activities.Reports.PurchaseReportsActivity;
import com.example.statspos.Activities.Reports.SalesReportsActivity;
import com.example.statspos.Adapters.Purchase.ItemsPurchaseReportAdapter;
import com.example.statspos.Adapters.Sales.ItemsSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Items.Categories;
import com.example.statspos.Models.Items.SubCategories;
import com.example.statspos.Models.Reports.Purchase.ItemsPurchaseReport;
import com.example.statspos.Models.Reports.Sales.ItemsSalesReport;
import com.example.statspos.R;
import com.example.statspos.databinding.FragmentCategoryPurchaseReportBinding;
import com.example.statspos.databinding.FragmentCategorySalesReportBinding;
import com.example.statspos.databinding.ItemsPurchaseReportHelperBinding;
import com.example.statspos.databinding.ItemsSalesReportHelperBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryPurchaseReportFragment extends Fragment {

    FragmentCategoryPurchaseReportBinding binding;
    ItemsPurchaseReportHelperBinding bindingInclude;

    ItemsPurchaseReportAdapter itemsPurchaseReportAdapter;
    List<ItemsPurchaseReport> list;
    HP.ObjectRequest objectRequest;
    PurchaseReportsActivity purchaseReportsActivity;
    Categories selectedCategory = null;
    SubCategories selectedSubCategory = null;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoryPurchaseReportBinding.bind(inflater.inflate(R.layout.fragment_category_purchase_report, container, false));
        bindingInclude = ItemsPurchaseReportHelperBinding.bind(binding.getRoot());

        init();

        return binding.getRoot();
    }

    private void init(){
        purchaseReportsActivity = (PurchaseReportsActivity) getActivity();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

        loadCategories();

        list = new ArrayList<>();
        itemsPurchaseReportAdapter = new ItemsPurchaseReportAdapter(getContext(), list);

        bindingInclude.recyclerView.setAdapter(itemsPurchaseReportAdapter);
        bindingInclude.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectRequest = new HP.ObjectRequest(getContext(), "reports/purchase/itemsPurchaseReport", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    JSONArray rows = response.getJSONArray("rows");
                    if(rows.length() > 0){
                        JSONObject total = response.getJSONObject("total");
                        binding.grandTotal.setText(HP.formatCurrency(total.getString("grandTotal")));

                        String totalQty = "Pcs = " + total.getString("totalQty") + ", Crtn = " + total.getString("totalCrtn");
                        binding.totalQtyLabel.setText(totalQty);

                        for(int i = 0; i < rows.length(); i++){
                            ItemsPurchaseReport itemsPurchaseReport = gson.fromJson(rows.getJSONObject(i).toString(), ItemsPurchaseReport.class);
                            list.add(itemsPurchaseReport);
                        }
                    }else {
                        binding.grandTotal.setText("0.00");
                        binding.totalQtyLabel.setText("Pcs = 0, Crtn = 0");
                    }

                    itemsPurchaseReportAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Categories
        binding.categoryRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCategoryReport();
            }
        });

        binding.categoryDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.categoryTB.showDropDown();
            }
        });

        binding.categoryTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.categoryTB.showDropDown();
                }
            }
        });

        binding.categoryTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = (Categories) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                binding.subCategoryTB.setText("");
                selectedSubCategory = null;
                loadSubCategories();
                loadCategoryReport();
            }
        });

        // Sub-Categories
        binding.subCategoryRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSubCategoryReport();
            }
        });

        binding.subCategoryDropdownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.subCategoryTB.showDropDown();
            }
        });

        binding.subCategoryTB.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    binding.subCategoryTB.showDropDown();
                }
            }
        });

        binding.subCategoryTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubCategory = (SubCategories) adapterView.getItemAtPosition(i);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                loadSubCategoryReport();
            }
        });


    }

    private void loadCategories(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "categories/searchCategory", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<Categories> categoriesList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        Categories categorie = gson.fromJson(jsonArray.getJSONObject(i).toString(), Categories.class);
                        categoriesList.add(categorie);
                    }

                    ArrayAdapter<Categories> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, categoriesList);
                    binding.categoryTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("text", "");
        objectRequest.request(params2);
    }

    private void loadSubCategories(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(getContext(), "categories/searchSubCategory", new HP.ObjectRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                List<SubCategories> subCategoriesList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        SubCategories subCategory = gson.fromJson(jsonArray.getJSONObject(i).toString(), SubCategories.class);
                        subCategoriesList.add(subCategory);
                    }

                    ArrayAdapter<SubCategories> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, subCategoriesList);
                    binding.subCategoryTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("category_id", selectedCategory.getId());
        params2.put("text", "");

        if(selectedCategory != null){
            objectRequest.request(params2);
        }
    }

    private void loadCategoryReport(){
        if(selectedCategory != null) {
            progressDialog.show();
            objectRequest.request(getParamsCategories());
        }
    }

    private void loadSubCategoryReport(){
        if(selectedSubCategory != null) {
            progressDialog.show();
            objectRequest.request(getParamsSubCategories());
        }
    }

    private Map<String, String> getParamsCategories(){
        Map<String, String> params = new HashMap<>();
        params.put("report_by", "category");
        params.put("id", selectedCategory.getId());

        params.putAll(purchaseReportsActivity.getDateParams());
        params.putAll(purchaseReportsActivity.getRBParams());

        return params;
    }

    private Map<String, String> getParamsSubCategories(){
        Map<String, String> params = new HashMap<>();
        params.put("report_by", "sub_category");
        params.put("id", selectedSubCategory.getId());

        params.putAll(purchaseReportsActivity.getDateParams());
        params.putAll(purchaseReportsActivity.getRBParams());

        return params;
    }

    @Override
    public void onResume() {
        super.onResume();
        purchaseReportsActivity.setRadioButtonsVisibility(true);
    }

}