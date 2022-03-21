package com.example.bcicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.KeyListener;
import android.text.method.TextKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

/**
 * @author yuchen
 */
public class PersonalDataActivity extends AppCompatActivity {

    private static final String TAG = "PersonalDataActivity";

    TextView tv_change_avatars;
    TextView tv_real_name;
    TextView tv_gender;
    TextView tv_patient_type;
    TextView tv_attending_doctor;
    TextView tv_medicine_method;
    TextView tv_device_ip;
    TextView tv_guardian_mode;
    TextView tv_guardian_name;
    TextView tv_guardian_phone;
    TextView tv_logout;
    Switch switch_guardian_mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        getWindow().setStatusBarColor(Color.WHITE);

        // 绑定控件
        bindView();
        // 初始化布局
        initView();
        // 初始化数据
        initData();
        // 事件监听
        initEvent();

    }


    /**
     * 绑定控件
     */
    private void bindView() {

        tv_change_avatars = findViewById(R.id.tv_change_avatars);
        tv_real_name = findViewById(R.id.tv_real_name);
        tv_gender = findViewById(R.id.tv_gender);
        tv_patient_type = findViewById(R.id.tv_patient_type);
        tv_attending_doctor = findViewById(R.id.tv_attending_doctor);
        tv_medicine_method = findViewById(R.id.tv_medicine_method);
        tv_device_ip = findViewById(R.id.tv_device_ip);
        tv_guardian_mode = findViewById(R.id.tv_guardian_mode);
        tv_guardian_name = findViewById(R.id.tv_guardian_name);
        tv_guardian_phone = findViewById(R.id.tv_guardian_phone);
        tv_logout = findViewById(R.id.tv_logout);
        switch_guardian_mode = findViewById(R.id.switch_guardian_mode);

    }

    /**
     * 初始化数据
     */
    private void initData() {

    }

    /**
     * 初始化布局
     */
    private void initView() {
        // 初始化toolbar
        initToolbar();

        tv_real_name.setText("张三");
        tv_gender.setText("男");
        tv_patient_type.setText("癫痫");
        tv_attending_doctor.setText("钟南山");
        tv_medicine_method.setText("已置入");
        tv_device_ip.setText("192.168.55.1:22");
        tv_guardian_mode.setText(R.string.hint_guardian_mode_off);
        tv_guardian_name.setText("李四");
        tv_guardian_phone.setText("18016283979");


    }


    /**
     * 事件监听
     */
    private void initEvent() {

        // 更改头像
        tv_change_avatars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PersonalDataActivity.this, "点击了更改头像", Toast.LENGTH_SHORT).show();
            }

        });

        // 监护人模式
        switch_guardian_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switch_guardian_mode.isChecked()) {
                    tv_guardian_mode.setText(R.string.hint_guardian_mode_on);
                } else {
                    tv_guardian_mode.setText(R.string.hint_guardian_mode_off);
                }
            }
        });

        // 退出登录
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDataActivity.this, LoginActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }

    /**
     * 初始化 toolbar
     */
    protected void initToolbar() {

        String title = "个人资料";
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 设置title
        // getSupportActionBar().setTitle(title);
        TextView textView = findViewById(R.id.tv_toolbar_title);
        // 添加默认的返回图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        textView.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}