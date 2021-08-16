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

        initToolbar();
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