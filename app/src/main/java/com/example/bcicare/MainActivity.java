package com.example.bcicare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView tv_edit_info;
    ImageView iv_qr_code;
    TextView tv_icon_status;
    TextView tv_icon_feeling;
    TextView tv_icon_fatigue;
    TextView tv_fatigue_line;
    TextView tv_feeling_line;
    TextView tv_title_txt;
    TextView tv_refresh_line;
    Button btn_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        bindView();
        // 初始化数据
        initData();
        // 事件监听
        initEvent();

    }

    /**
     * 绑定控件
     */
    private void bindView(){
        tv_edit_info = findViewById(R.id.tv_edit_info);
        iv_qr_code = findViewById(R.id.iv_qr_code);
        tv_icon_status = findViewById(R.id.tv_icon_status);
        tv_icon_feeling = findViewById(R.id.tv_icon_feeling);
        tv_icon_fatigue = findViewById(R.id.tv_icon_fatigue);
        tv_fatigue_line = findViewById(R.id.tv_fatigue_line);
        tv_feeling_line = findViewById(R.id.tv_feeling_line);
        tv_title_txt = findViewById(R.id.tv_title_txt);
        tv_refresh_line = findViewById(R.id.tv_refresh_line);
        btn_contact = findViewById(R.id.btn_contact);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        // 图标边框设置颜色
        GradientDrawable gd_status = (GradientDrawable) tv_icon_status.getBackground();
        gd_status.setStroke(1, getResources().getColor(R.color.text_status));
        GradientDrawable gd_feeling = (GradientDrawable) tv_icon_feeling.getBackground();
        gd_feeling.setStroke(1, getResources().getColor(R.color.text_feeling));
        GradientDrawable gd_fatigue = (GradientDrawable) tv_icon_fatigue.getBackground();
        gd_fatigue.setStroke(1, getResources().getColor(R.color.text_fatigue));
    }

    /**
     * 事件监听
     */
    private void initEvent() {
        // 编辑个人信息
        tv_edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "编辑个人信息", Toast.LENGTH_SHORT).show();
            }
        });

        // 预约码
        iv_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "预约码", Toast.LENGTH_SHORT).show();
            }
        });

        // 疲劳曲线
        tv_fatigue_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_title_txt.setText("12h疲劳检测曲线");
                tv_refresh_line.setText("上次更新：21时");
                // 更新图表
                // ...
            }
        });

        // 情感曲线
        tv_feeling_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_title_txt.setText("8h情感检测曲线");
                tv_refresh_line.setText("上次更新：22时");
                // 更新图表
                // ...
            }
        });

        // 联系主治医师
        btn_contact.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "联系主治医师", Toast.LENGTH_SHORT).show();
            }
        });
    }
}