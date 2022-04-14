package com.example.statspos.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.statspos.Adapters.Items.ItemsAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Items.Items;
import com.example.statspos.databinding.ActivityItemsBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemsActivity extends AppCompatActivity {

    ActivityItemsBinding  binding;
    HP.ArrayRequest getItemArrayRequest;
    HP.ArrayRequest searchItemsArrayRequest;
    HP.PostRequest insertTempPostRequest;
    ItemsAdapter itemsAdapter;
    List<Items> list;
    Items selectedItem = null;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        loadItems();

        list = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, list, new ItemsAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                selectedItem = list.get(position);
                setData();
            }
        });
        binding.recyclerView.setAdapter(itemsAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initRequests();
        initOnClickListeners();
    }

    private void initRequests(){
        // Request to getItem
        getItemArrayRequest = new HP.ArrayRequest(this, "items/getItem", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length() > 0){
                        Gson gson = new Gson();
                        selectedItem = gson.fromJson(response.getJSONObject(0).toString(), Items.class);
                        hideKeyboard();
                        setData();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Request to searchItems
        searchItemsArrayRequest = new HP.ArrayRequest(this, "items/searchItems", new HP.ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    list.clear();
                    Gson gson = new Gson();

                    if(response.length() > 0){
                        for(int i = 0; i < response.length(); i++){
                            Items item = gson.fromJson(response.getJSONObject(i).toString(), Items.class);
                            list.add(item);
                        }
                    }

                    hideKeyboard();
                    itemsAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Request to insert temp data
        insertTempPostRequest = new HP.PostRequest(this, "temp/insertTemp", new HP.PostRequest.OnResponseHandler() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                clearData();
            }
        });

    }

    private void initOnClickListeners(){
        // BarcodeTB click listener
        binding.barcodeTbLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem();
            }
        });

        binding.barcodeTB.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = (Items) adapterView.getItemAtPosition(i);
                getItem();
                hideKeyboard();
            }
        });

        binding.barcodeTB.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    getItem();
                    return true;
                }
                return false;
            }
        });

        // SearchTB click listener
        binding.searchTbLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchItems();
            }
        });

        binding.searchTB.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    searchItems();
                    return true;
                }
                return false;
            }
        });

        // Save button click listener
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("table_name", "Items");
                    jsonObject.put("data", new Gson().toJson(getData()));
                    jsonObject.put("action", "update");

                    insertTempPostRequest.request(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // Cancel button click listener
        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });
    }

    private void loadItems(){
        HP.ObjectRequest objectRequest = new HP.ObjectRequest(this, "items/loadItems", new HP.ObjectRequest.OnResponseHandler() {
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

                    ArrayAdapter<Items> arrayAdapter = new ArrayAdapter<>(ItemsActivity.this, android.R.layout.simple_list_item_1, itemsList);
                    binding.barcodeTB.setAdapter(arrayAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        objectRequest.request(params2);
    }

    private void getItem(){
        String text = binding.barcodeTB.getText().toString();
        if(text != "") {
            progressDialog.show();
            Map<String, String> params = new HashMap<>();
            params.put("text", text);
            getItemArrayRequest.request(params);
        }
    }

    private void searchItems(){
        String text = binding.searchTB.getText().toString();
        if(text != "") {
            progressDialog.show();
            Map<String, String> params = new HashMap<>();
            params.put("text", text);
            searchItemsArrayRequest.request(params);
        }
    }

    private void setData(){
        binding.barcodeTB.setText(selectedItem.getBarcode());
        binding.refCodeTB.setText(selectedItem.getRef_code());
        binding.itemnameTB.setText(selectedItem.getItemname());
        binding.costTB.setText(String.valueOf(selectedItem.getCost()));
        binding.retailTB.setText(String.valueOf(selectedItem.getRetail()));
        binding.wSaleTB.setText(String.valueOf(selectedItem.getW_sale()));
        binding.crtnRateTB.setText(String.valueOf(selectedItem.getCrtn_rate()));
    }

    private Items getData(){
        Items item = new Items();

        item.setId(selectedItem.getId());
        item.setItemname(binding.itemnameTB.getText().toString());
        item.setRef_code(binding.refCodeTB.getText().toString());
        item.setCost(Float.valueOf(binding.costTB.getText().toString()));
        item.setRetail(Float.valueOf(binding.retailTB.getText().toString()));
        item.setW_sale(Float.valueOf(binding.wSaleTB.getText().toString()));
        item.setCrtn_rate(Float.valueOf(binding.crtnRateTB.getText().toString()));

        return item;
    }

    private void clearData(){
        selectedItem = null;

        binding.barcodeTB.setText("");
        binding.refCodeTB.setText("");
        binding.itemnameTB.setText("");
        binding.costTB.setText("");
        binding.retailTB.setText("");
        binding.wSaleTB.setText("");
        binding.crtnRateTB.setText("");

        binding.barcodeTB.requestFocus();
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

}