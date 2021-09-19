package com.example.bcicare.utils;


import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {

    private static final String TAG = "OkHttpUtil";
    private OkHttpClient okHttpClient;
    Request request;
    Call call;
    Response response;

    public OkHttpUtil() {
        okHttpClient = new OkHttpClient();
    }

    public String doGetSync(String url, Map<String, String> params) {
        StringBuffer buffer = new StringBuffer();
//        HttpUrl.Builder httpBuilder = new HttpUrl.Builder().host(url);
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                httpBuilder.addQueryParameter(key, params.get(key));
            }
        }
        request = new Request.Builder().url(httpBuilder.build()).get().build();
        call = okHttpClient.newCall(request);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = call.execute();
                    String str = response.body().string();
                    buffer.append(str);
                } catch (Exception e) {
                    e.printStackTrace();
                    buffer.append("请求失败: " + e.getMessage());
                }
            }
        });
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return buffer.toString();

    }

    public String doGetAsync(String url, Map<String, String> params) throws InterruptedException {
        StringBuffer buffer = new StringBuffer();
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                httpBuilder.addQueryParameter(key, params.get(key));
            }
        }
        request = new Request.Builder().url(httpBuilder.build()).build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                buffer.append("请求失败:").append(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                buffer.append(response.body().string());
                Log.d(TAG, "onResponse: " + buffer.toString());
            }
        });
        while ("".equals(buffer.toString())) {
            Thread.sleep(500);
        }

        return buffer.toString();
    }

    public void doGetAsyncCallback(String url,Map<String, String> params, Callback callback) {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(url).newBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                httpBuilder.addQueryParameter(key, params.get(key));
            }
        }
        request = new Request.Builder().url(httpBuilder.build()).build();
        call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }



    public String doPostSync(String url, Map<String, String> params) {
        StringBuffer buffer = new StringBuffer();
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        request = new Request.Builder().url(url).post(builder.build()).build();
        call = okHttpClient.newCall(request);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = call.execute();
                    String str = response.body().string();
                    buffer.append(str);
                } catch (Exception e) {
                    e.printStackTrace();
                    buffer.append("请求失败: " + e.getMessage());
                }
            }
        });
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public void doPostAsyncCallback(String url, Map<String, String> params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
        request = new Request.Builder().url(url).post(builder.build()).build();
        call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

}
