package com.example.bcicare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcicare.auth.LoginDTO;
import com.example.bcicare.config.Urls;
import com.example.bcicare.utils.OkHttpUtil;
import com.example.bcicare.utils.SharedPreferencesUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText et_username;
    EditText et_password;
    CheckBox cb_remember_pd;
    TextView tv_forgot_pd;
    TextView tv_login;
    TextView tv_register;

    View preLoadMainHome = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // 绑定控件
        bindView();
        // 初始化数据
        initData();
        // 事件监听
        initEvent();


    }


    protected void preLoadResource() {
        if (preLoadMainHome == null) {
            preLoadMainHome = View.inflate(this, R.layout.activity_main, null);
        }
    }

    /**
     * 绑定控件
     */
    private void bindView() {
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        cb_remember_pd = findViewById(R.id.cb_remember_pd);
        tv_forgot_pd = findViewById(R.id.tv_forgot_pd);
        tv_login = findViewById(R.id.tv_login);
        tv_register = findViewById(R.id.tv_register);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        boolean isRemember = SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA").getBoolean("is_remember", false);
        cb_remember_pd.setChecked(isRemember);
        if (isRemember) {
            String username = SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA").getString("username");
            String password = SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA").getString("password");
            et_username.setText(username);
            et_password.setText(password);
        }
    }

    /**
     * 事件监听
     */
    private void initEvent() {

        // 忘记密码
        tv_forgot_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d(TAG, "点击了忘记密码");
                Toast.makeText(LoginActivity.this, "点击了忘记密码", Toast.LENGTH_SHORT).show();
            }
        });

        // 登录
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 注册
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.d(TAG, "点击了注册");
                Toast.makeText(LoginActivity.this, "点击了注册", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        // 获取用户名密码
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        // 判断用户名和密码是否为空
        if (username.isEmpty()) {
            // 用户名为空
            Toast.makeText(LoginActivity.this, "请输入账号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            // 密码为空
            Toast.makeText(LoginActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }
        // 判断账号密码是否匹配
        if (checkPassword(username, password)) {
            Log.d(TAG, "密码正确");
            // 记住密码
            if (cb_remember_pd.isChecked()) {
                SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA")
                        .putBoolean("is_remember", true)
                        .putString("username", username)
                        .putString("password", password);
            } else {
                SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA")
                        .putBoolean("is_remember", false);
            }
            Log.d(TAG, "记住密码" + SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA").getBoolean("is_remember"));
            Log.d(TAG, "保存的用户名" + SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA").getString("username"));
            Log.d(TAG, "保存的密码" + SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA").getString("password"));
            // 跳转到主界面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        } else {
            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
//            Log.d(TAG, "用户名" + et_username.getText().toString());
//            Log.d(TAG, "密码" + et_password.getText().toString());

        }
    }

    /**
     * 判断账号密码是否匹配
     *
     * @param user_email 用户名
     * @param password   密码
     * @return boolean
     */
    private boolean checkPassword(String user_email, String password) {
        Log.d(TAG, "判断账号密码是否匹配:" + user_email);
        Log.d(TAG, "输入的用户名:" + user_email);
        Log.d(TAG, "输入的密码:" + password);

        String url = Urls.BASE_URL + Urls.AUTH_LOGIN;

        Log.d(TAG, "checkPassword: url " + url);

        String res = OkHttpUtil.builder().url(url).addParam("user_email", user_email).addParam("password", password).addHeader("Content-Type", "application/json; charset=utf-8").post().sync();

        Log.d(TAG, "checkPassword: 登录结果" + res);

        Log.d(TAG, "checkPassword: 登录结果" + res.contains("access_token"));

        if (res.contains("access_token")) {
            Gson gson = new Gson();
            LoginDTO loginDTO = gson.fromJson(res, LoginDTO.class);
            SharedPreferencesUtil.init(LoginActivity.this, "USER_DATA")
                    .putString("access_token", loginDTO.getAccessToken())
                    .putString("refresh_token", loginDTO.getRefreshToken());
            return true;
        }
        return false;
    }

}