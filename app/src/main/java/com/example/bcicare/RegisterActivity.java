package com.example.bcicare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcicare.api.UserRegisterDTO;
import com.example.bcicare.config.Urls;
import com.example.bcicare.utils.OkHttpUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * @author yuchen
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText et_register_email;
    EditText et_password1;
    EditText et_confirm_password;
    TextView tv_register_btn;
    TextView tv_toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);

        // 绑定控件
        bindView();
        // 事件监听
        initEvent();
    }

    /**
     * 绑定控件
     */
    private void bindView() {
        et_register_email = findViewById(R.id.et_register_email);
        et_password1 = findViewById(R.id.et_password1);
        et_confirm_password = findViewById(R.id.et_confirm_password);
        tv_register_btn = findViewById(R.id.tv_register_btn);
        tv_toLogin = findViewById(R.id.tv_toLogin);
    }

    /**
     * 事件监听
     */
    private void initEvent() {

        // 注册
        tv_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        // 跳转到登录界面
        tv_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }

    /**
     * 注册
     */
    private void register() {

        // 获取邮箱密码
        String email = et_register_email.getText().toString().trim();
        String password = et_password1.getText().toString().trim();
        String confirmPassword = et_confirm_password.getText().toString().trim();

        // 判断用户名和密码是否为空
        if (email.isEmpty()) {
            // 用户名为空
            Toast.makeText(RegisterActivity.this, "请输入邮箱！", Toast.LENGTH_SHORT).show();
            return;
        } else if (password.isEmpty()) {
            // 密码为空
            Toast.makeText(RegisterActivity.this, "请输入密码！", Toast.LENGTH_SHORT).show();
            return;
        } else if (confirmPassword.isEmpty()) {
            // 确认密码为空
            Toast.makeText(RegisterActivity.this, "请确认密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "密码不一致！", Toast.LENGTH_SHORT).show();
            // 清空输入框
            et_confirm_password.setText("");
            return;
        }

        if (sendRegisterRequest(email, password)) {
            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
            // 跳转到登录界面
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            RegisterActivity.this.finish();

        } else {
            Toast.makeText(RegisterActivity.this, "该邮箱已注册！", Toast.LENGTH_SHORT).show();
            // 清空输入框
            et_register_email.setText("");
            et_password1.setText("");
            et_confirm_password.setText("");
        }


    }

    /**
     * 发送注册请求
     *
     * @param email    邮箱
     * @param password 密码
     * @return boolean
     */
    private boolean sendRegisterRequest(String email, String password) {
        Log.d(TAG, "sendRegisterRequest" + "发送请求");

        String url = Urls.USER_REGISTER;

        String res = OkHttpUtil.builder().url(url)
                .addParam("user_email", email)
                .addParam("password", password)
                .addParam("user_type", "0")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post().async();
        Log.d(TAG, "sendRegisterRequest: 结果 " + res);

        Gson gson = new Gson();
        UserRegisterDTO userRegisterDTO = gson.fromJson(res, UserRegisterDTO.class);
        int code = userRegisterDTO.getCode();

        Log.d(TAG, "sendRegisterRequest: code " + code);

        if (code == 1){
            Log.d(TAG, "sendRegisterRequest: 注册成功");
            return true;
        }

        return false;

    }

}