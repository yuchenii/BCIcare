package com.example.bcicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

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

        tv_change_avatars.findViewById(R.id.tv_change_avatars);
        tv_real_name.findViewById(R.id.tv_real_name);
        tv_gender.findViewById(R.id.tv_gender);
        tv_patient_type.findViewById(R.id.tv_patient_type);
        tv_attending_doctor.findViewById(R.id.tv_attending_doctor);
        tv_medicine_method.findViewById(R.id.tv_medicine_method);
        tv_device_ip.findViewById(R.id.tv_device_ip);
        tv_guardian_mode.findViewById(R.id.tv_guardian_mode);
        tv_guardian_name.findViewById(R.id.tv_guardian_name);
        tv_guardian_phone.findViewById(R.id.tv_guardian_phone);
        tv_logout.findViewById(R.id.tv_logout);
        switch_guardian_mode.findViewById(R.id.switch_guardian_mode);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        // 初始化toolbar
        initToolbar();
    }

    /**
     * 初始化数据
     */
    private void initData(){

    }

    /**
     * 事件监听
     */
    private void initEvent(){

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 添加默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        textView.setText(title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}