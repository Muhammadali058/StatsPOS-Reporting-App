package com.example.statspos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.statspos.Models.TotalSalesReport;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HP {
    public static String getUrl(String url, Map<String, String> mParams){
        StringBuilder stringBuilder = new StringBuilder(url);
        int i = 1;
        for (Map.Entry<String,String> entry: mParams.entrySet()) {
            String key;
            String value;
            try {
                key = URLEncoder.encode(entry.getKey(), "UTF-8");
                value = URLEncoder.encode(entry.getValue(), "UTF-8");
                if(i == 1) {
                    stringBuilder.append("?" + key + "=" + value);
                } else {
                    stringBuilder.append("&" + key + "=" + value);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;

        }

        return stringBuilder.toString();
    }

    public static class ObjectRequest {
        Context context;
        String url;
        Map<String, String> params;
        OnResponseHandler onResponseHandler;

        public ObjectRequest(Context context, String url, Map<String, String> params, OnResponseHandler onResponseHandler) {
            this.context = context;
            this.url = url;
            this.params = params;
            this.onResponseHandler = onResponseHandler;

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HP.getUrl(url, params), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    onResponseHandler.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error = ", error.toString());
                }
            });

            requestQueue.add(jsonObjectRequest);
        }

        public interface OnResponseHandler{
            void onResponse(JSONObject response);
        }
    }

    public static class ArrayRequest {
        Context context;
        String url;
        Map<String, String> params;
        OnResponseHandler onResponseHandler;

        public ArrayRequest(Context context, String url, Map<String, String> params, OnResponseHandler onResponseHandler) {
            this.context = context;
            this.url = url;
            this.params = params;
            this.onResponseHandler = onResponseHandler;

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, HP.getUrl(url, params), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    onResponseHandler.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error = ", error.toString());
                }
            });

            requestQueue.add(jsonArrayRequest);
        }

        public interface OnResponseHandler{
            void onResponse(JSONArray response);
        }
    }
}
