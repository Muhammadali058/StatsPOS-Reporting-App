package com.example.statspos;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.statspos.Models.Settings;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Calendar;
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

    public static class PostRequest {
        Context context;
        String url;
        OnResponseHandler onResponseHandler;

        public PostRequest(Context context, String url, OnResponseHandler onResponseHandler) {
            this.context = context;
            this.url = api + url;
            this.onResponseHandler = onResponseHandler;
        }

        public void request(JSONObject jsonObject){
            try {
                jsonObject.put("b_code", b_code);
                jsonObject.put("s_no", b_code);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Log.i("Response = ", response.toString());
                    onResponseHandler.onResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.i("Error = ", error.toString());
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/json");
                    return params;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody(){
                    try {
                        return jsonObject.toString().getBytes("utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return  null;
                    }
                }
            };

            requestQueue.add(stringRequest);
        }

        public interface OnResponseHandler{
            void onResponse(String response);
        }
    }

    public static class MultipartPostRequest {
        Context context;
        String url;
        OnResponseHandler onResponseHandler;

        public MultipartPostRequest(Context context, String url, OnResponseHandler onResponseHandler) {
            this.context = context;
            this.url = api + url;
            this.onResponseHandler = onResponseHandler;
        }

        public void request(Map<String, VolleyMultipartRequest.DataPart> params){
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    onResponseHandler.onResponse(new String(response.data));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.i("Error = ", error.toString());
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, DataPart> getByteData() {
//                    Map<String, DataPart> params = new HashMap<>();
//                    params.put("image", new DataPart("filename.jpg", AppHelper.getFileDataFromDrawable(context, binding.imageView.getDrawable()), "image/jpeg"));
                    return params;
                }
            };

            requestQueue.add(multipartRequest);
        }

        public interface OnResponseHandler{
            void onResponse(String response);
        }
    }

    public static byte[] getFileDataFromBitmap(Context context, Bitmap bitmap) {
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static class OnDateClickListener implements View.OnClickListener {
        Context context;
        OnDateSet onDateSet;

        public OnDateClickListener(Context context, OnDateSet onDateSet) {
            this.context = context;
            this.onDateSet = onDateSet;
        }

        @Override
        public void onClick(View view) {
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int month = calendar.get(Calendar.MONTH);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            month = month + 1;

                            String dayString = String.valueOf(day);
                            String monthString = String.valueOf(month);
                            String yearString = String.valueOf(year);

                            if(day<10){
                                dayString = "0" + dayString;
                            }

                            if(month<10){
                                monthString = "0" + monthString;
                            }

                            String date = dayString + "-" + monthString + "-" + yearString;

                            onDateSet.onDateSet(date);
                        }
                    }, year, month, day);

//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            datePickerDialog.show();
        }

        public interface OnDateSet{
            void onDateSet(String date);
        }

    }

    public static String reverseDate(String date){
        String reversedDate = date;

        if(date.contains("/")){
            String[] dates = date.split("/");
            reversedDate = dates[2] + "/" + dates[1] + "/" + dates[0];
        }else if(date.contains("-")){
            String[] dates = date.split("-");
            reversedDate = dates[2] + "-" + dates[1] + "-" + dates[0];
        }

        return reversedDate;
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dayString = String.valueOf(day);
        String monthString = String.valueOf(month);
        String yearString = String.valueOf(year);

        if(day<10){
            dayString = "0" + dayString;
        }

        if(month<10){
            monthString = "0" + monthString;
        }

        String date = dayString + "-" + monthString + "-" + yearString;

        return date;
    }

}
