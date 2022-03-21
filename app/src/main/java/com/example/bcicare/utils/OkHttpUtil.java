package com.example.bcicare.utils;

import android.util.Log;


import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    private static final String TAG = "OkHttpUtil";
    private static volatile OkHttpClient okHttpClient = null;
    private static volatile Semaphore semaphore = null;
    private Map<String, String> headerMap;
    private Map<String, String> paramMap;
    private String url;
    private Request.Builder request;


    private OkHttpUtil() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(20, TimeUnit.SECONDS)
                            .hostnameVerifier((hostName, session) -> true)
                            .retryOnConnectionFailure(true)
                            .build();
                    addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36");
                }
            }
        }
    }

    /**
     * 用于异步请求时，控制访问线程数，返回结果
     *
     * @return semaphore
     */
    private static Semaphore getSemaphoreInstance() {
        //只能1个线程同时访问
        synchronized (OkHttpUtil.class) {
            if (semaphore == null) {
                semaphore = new Semaphore(0);
            }
        }
        return semaphore;
    }

    /**
     * 创建 OkHttpUtil
     *
     * @return OkHttpUtil
     */
    public static OkHttpUtil builder() {
        return new OkHttpUtil();
    }

    /**
     * 添加 url
     *
     * @param url 链接
     * @return OkHttpUtil
     */
    public OkHttpUtil url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 添加请求头
     *
     * @param key   参数名
     * @param value 参数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addHeader(String key, String value) {
        if (headerMap == null) {
            headerMap = new LinkedHashMap<>(16);
        }
        headerMap.put(key, value);
        return this;
    }


    /**
     * 添加单个参数
     *
     * @param key   参数名
     * @param value 参数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new LinkedHashMap<>(16);
        }
        paramMap.put(key, value);
        return this;
    }

    /**
     * 添加多个参数
     *
     * @param params 参数
     * @return OkHttpUtil
     */
    public OkHttpUtil addParams(Map<String, String> params) {
        paramMap = params;
        return this;
    }


    /**
     * 初始化get方法
     *
     * @return OkHttpUtil
     */
    public OkHttpUtil get() {
        request = new Request.Builder();
        StringBuilder urlBuilder = new StringBuilder(url);
        // 添加参数
        if (paramMap != null) {
            urlBuilder.append("?");
            for (String key : paramMap.keySet()) {
                urlBuilder.append(key).append("=").append(paramMap.get(key)).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        request.url(urlBuilder.toString());
        return this;
    }

    /**
     * 初始化post方法
     * 本项目前后端分离，全采用application/json，故默认为application/json
     * 需要其他形式可在 post() 里添加参数进行判断
     *
     * @return OkHttpUtil
     */
    public OkHttpUtil post() {
        RequestBody requestBody;
        String json = "";
        if (paramMap != null) {
            json = new Gson().toJson(paramMap);
        }
        requestBody = RequestBody.create(json, MediaType.parse("application/json;charset=utf-8"));
        request = new Request.Builder().post(requestBody).url(url);
        return this;
    }

    /**
     * 同步请求
     *
     * @return 请求返回的字符串
     */
    public String sync() {
        setHeader(request);
        try {
            Response response = okHttpClient.newCall(request.build()).execute();
            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "请求失败：" + e.getMessage();
        }
    }

    /**
     * 异步请求，有返回值
     */
    public String async() {
        StringBuilder buffer = new StringBuilder("");
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                buffer.append("请求出错：").append(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                buffer.append(response.body().string());
                getSemaphoreInstance().release();
            }
        });
        try {
            getSemaphoreInstance().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 异步请求，带有接口回调
     *
     * @param callBack 接口回调
     */
    public void async(ICallBack callBack) {
        setHeader(request);
        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                assert response.body() != null;
                callBack.onSuccessful(call, response.body().string());
            }
        });
    }


    /**
     * 为request添加请求头
     *
     * @param request request
     */
    private void setHeader(Request.Builder request) {
        if (headerMap != null) {
            try {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 自定义一个接口回调
     */
    public interface ICallBack {

        /**
         * 成功的回调函数
         *
         * @param call call
         * @param data 返回的数据
         */
        void onSuccessful(Call call, String data);

        /**
         * 失败的回调函数
         *
         * @param call call
         * @param errorMsg 错误信息
         */
        void onFailure(Call call, String errorMsg);

    }
}
