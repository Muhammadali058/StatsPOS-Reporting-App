package com.example.statspos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.statspos.Adapters.TotalSalesReportAdapter;
import com.example.statspos.Models.TotalSalesReport;
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

//        String url = "http://waqeehaidar-001-site1.itempurl.com/api/reports/sales/totalSalesReport";
        String url = "http://192.168.0.101:805/api/reports/sales/totalSalesReport";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap<>();
        params.put("b_code", "1");
        params.put("s_no", "1");
        params.put("date_from", "2022/03/09");
        params.put("date_to", "2022/03/09");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HP.getUrl(url, params), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                try {
                    list = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("rows");
                    for(int i = 0; i < jsonArray.length(); i++){
                        TotalSalesReport totalSalesReport = gson.fromJson(jsonArray.getJSONObject(i).toString(), TotalSalesReport.class);
                        list.add(totalSalesReport);
                    }

                    setTotalSalesReportAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    void setTotalSalesReportAdapter(){
        totalSalesReportAdapter = new TotalSalesReportAdapter(this, list);
        binding.recyclerView.setAdapter(totalSalesReportAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}