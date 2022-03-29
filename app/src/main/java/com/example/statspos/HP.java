package com.example.statspos;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.statspos.Models.Settings;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class HP {

    public static Settings settings = null;
    public static String b_code = "1";
    public static String s_no = "1";
//    public static String api = "http://waqeehaidar-001-site1.itempurl.com/api/";
    public static String api = "http://192.168.0.102:805/api/";

    public static void loadSettings(Context context){
        ArrayRequest arrayRequest = new ArrayRequest(context, "settings", new ArrayRequest.OnResponseHandler() {
            @Override
            public void onResponse(JSONArray response) {
                Gson gson = new Gson();
                if(response.length() > 0){
                    try {
                        settings = (Settings) gson.fromJson(response.getJSONObject(0).toString(), Settings.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Map<String, String> params = new HashMap<>();
        params.put("b_code", b_code);
        params.put("s_no", s_no);
        arrayRequest.request(params);
    }

    public static String formatCurrency(String number){
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(Double.parseDouble(number));
    }

    public static String getUrl(String url, Map<String, String> mParams){
        StringBuilder stringBuilder = new StringBuilder(url);
        int i = 1;
        for (Map.Entry<String,String> entry: mParams.entrySet()) {
            String key = entry.getKey(); // URLEncoder.encode(entry.getKey(), "UTF-8");
            String value = entry.getValue(); // URLEncoder.encode((String) entry.getValue(), "UTF-8");
            if(i == 1) {
                stringBuilder.append("?" + key + "=" + value);
            } else {
                stringBuilder.append("&" + key + "=" + value);
            }

            i++;

        }

        return stringBuilder.toString();
    }

    public static class ObjectRequest {
        Context context;
        String url;
        OnResponseHandler onResponseHandler;
        Map<String,String> mParams;

        public ObjectRequest(Context context, String url, OnResponseHandler onResponseHandler) {
            this.context = context;
            this.url = api + url;
            this.onResponseHandler = onResponseHandler;

            this.mParams = new HashMap<>();
            this.mParams.put("b_code", b_code);
            this.mParams.put("s_no", s_no);
        }

        public void request(Map<String,String> params){
            params.putAll(mParams);

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, HP.getUrl(url, params), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                    Log.i("Response = ", response.toString());
                    onResponseHandler.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.i("Error = ", error.toString());
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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
        OnResponseHandler onResponseHandler;
        Map<String,String> mParams;

        public ArrayRequest(Context context, String url, OnResponseHandler onResponseHandler) {
            this.context = context;
            this.url = api + url;
            this.onResponseHandler = onResponseHandler;

            this.mParams = new HashMap<>();
            this.mParams.put("b_code", "1");
            this.mParams.put("s_no", "1");
        }

        public void request(Map<String,String> params){
            params.putAll(mParams);

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, HP.getUrl(url, params), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
//                    Log.i("Response = ", response.toString());
                    onResponseHandler.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.i("Error = ", error.toString());
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(jsonArrayRequest);
        }
        public interface OnResponseHandler{
            void onResponse(JSONArray response);
        }
    }

}
