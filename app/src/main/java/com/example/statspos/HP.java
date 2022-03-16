package com.example.statspos;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

}
