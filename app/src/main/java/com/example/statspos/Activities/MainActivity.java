package com.example.statspos.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.statspos.Adapters.TotalSalesReportAdapter;
import com.example.statspos.HP;
import com.example.statspos.Models.Items;
import com.example.statspos.Models.Reports.TotalSalesReport;
import com.example.statspos.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TotalSalesReportAdapter totalSalesReportAdapter;
    List<TotalSalesReport> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = new ArrayList<>();
        totalSalesReportAdapter = new TotalSalesReportAdapter(this, list);
        binding.recyclerView.setAdapter(totalSalesReportAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Map<String, String> params = new HashMap<>();
        params.put("date_from", "2022/03/16");
        params.put("date_to", "2022/03/17");

//        HP.ObjectRequest objectRequest = new HP.ObjectRequest(this, "reports/sales/totalSalesReport", new HP.ObjectRequest.OnResponseHandler() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Gson gson = new Gson();
//                list.clear();
//                try {
//                    JSONArray jsonArray = response.getJSONArray("rows");
//                    for(int i = 0; i < jsonArray.length(); i++){
//                        TotalSalesReport totalSalesReport = gson.fromJson(jsonArray.getJSONObject(i).toString(), TotalSalesReport.class);
//                        list.add(totalSalesReport);
//                    }
//
//                    totalSalesReportAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        objectRequest.request(params);

        HP.ObjectRequest objectRequest = new HP.ObjectRequest(this, "items/searchItem", new HP.ObjectRequest.OnResponseHandler() {
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

                    ArrayAdapter<Items> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, itemsList);
                    binding.autoComplete.setAdapter(arrayAdapter);
                    binding.autoComplete.setThreshold(1);

                    binding.autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Items item = (Items) adapterView.getItemAtPosition(i);
                            Toast.makeText(MainActivity.this, item.getId() + " = " + item.getItemname(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Map<String, String> params2 = new HashMap<>();
        params2.put("text", "");
        objectRequest.request(params2);
    }
}