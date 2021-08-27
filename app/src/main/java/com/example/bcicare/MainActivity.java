package com.example.bcicare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcicare.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.bcicare.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.bcicare.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.bcicare.AAChartCoreLib.AAChartEnum.AAChartType;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    TextView tv_user_nickname;
    TextView tv_edit_info;
    ImageView iv_qr_code;
    TextView tv_icon_status;
    TextView tv_icon_feeling;
    TextView tv_icon_fatigue;
    TextView tv_status_detail;
    TextView tv_feeling_detail;
    TextView tv_fatigue_detail;
    TextView tv_fatigue_line;
    TextView tv_feeling_line;
    TextView tv_title_txt;
    TextView tv_refresh_line;
    Button btn_contact;

    AAChartView aaChartView;
    AAChartModel aaChartModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定控件
        bindView();
        // 初始化数据
        initData();
        // 初始化布局
        initView();
        // 事件监听
        initEvent();

    }

    /**
     * 绑定控件
     */
    private void bindView() {
        tv_user_nickname = findViewById(R.id.tv_user_nickname);
        tv_edit_info = findViewById(R.id.tv_edit_info);
        iv_qr_code = findViewById(R.id.iv_qr_code);
        tv_icon_status = findViewById(R.id.tv_icon_status);
        tv_icon_feeling = findViewById(R.id.tv_icon_feeling);
        tv_icon_fatigue = findViewById(R.id.tv_icon_fatigue);
        tv_status_detail = findViewById(R.id.tv_status_detail);
        tv_feeling_detail = findViewById(R.id.tv_feeling_detail);
        tv_fatigue_detail = findViewById(R.id.tv_fatigue_detail);
        tv_fatigue_line = findViewById(R.id.tv_fatigue_line);
        tv_feeling_line = findViewById(R.id.tv_feeling_line);
        tv_title_txt = findViewById(R.id.tv_title_txt);
        tv_refresh_line = findViewById(R.id.tv_refresh_line);
        btn_contact = findViewById(R.id.btn_contact);

        aaChartView = findViewById(R.id.AAChartView);
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
        // 图标边框设置颜色
        GradientDrawable gd_status = (GradientDrawable) tv_icon_status.getBackground();
        gd_status.setStroke(1, getResources().getColor(R.color.text_status));
        GradientDrawable gd_feeling = (GradientDrawable) tv_icon_feeling.getBackground();
        gd_feeling.setStroke(1, getResources().getColor(R.color.text_feeling));
        GradientDrawable gd_fatigue = (GradientDrawable) tv_icon_fatigue.getBackground();
        gd_fatigue.setStroke(1, getResources().getColor(R.color.text_fatigue));

        tv_user_nickname.setText("ID：回眸一笑");
        tv_status_detail.setText("间期");
        tv_feeling_detail.setText("平静");
        tv_fatigue_detail.setText("是");


        aaChartModel = new AAChartModel()
                .chartType(AAChartType.Area)
//                .title("THE HEAT OF PROGRAMMING LANGUAGE")
                .subtitle("单位(%)")
//                .subtitleAlign("left")
                .backgroundColor("#6DBEF8")
                .categories(new String[]{"10","11","12","13","14","15","16","17","18","19","20","21","22"})
                .dataLabelsEnabled(false)
                .yAxisGridLineWidth(1f)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("疲劳检测曲线")
                                .color("#5fb2f9")
                                .data(new Object[]{5,10,30,35,25,25,30,38,35,45,40,55,69}),
                });

        aaChartView.aa_drawChartWithChartModel(aaChartModel);


    }


    /**
     * 事件监听
     */
    private void initEvent() {
        // 编辑个人信息
        tv_edit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "编辑个人信息", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, PersonalDataActivity.class);
                startActivity(intent);
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
                // 修改文字
                tv_title_txt.setText(R.string.label_fatigue_title);
                tv_refresh_line.setText("上次更新：21时");
                btn_contact.setText(R.string.btn_contact);
                // 修改背景
                tv_feeling_line.setBackgroundResource(0);
                tv_fatigue_line.setBackgroundResource(R.drawable.bg_rounded_selected);
                // 更新图表
                // ...
                updateChart(0);
            }
        });

        // 情感曲线
        tv_feeling_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 修改文字
                tv_title_txt.setText(R.string.label_feeling_title);
                tv_refresh_line.setText("上次更新：22时");
                btn_contact.setText(R.string.btn_explain);
                // 修改背景
                tv_fatigue_line.setBackgroundResource(0);
                tv_feeling_line.setBackgroundResource(R.drawable.bg_rounded_selected);
                // 更新图表
                // ...
                updateChart(1);
            }
        });

        // 联系主治医师，数值说明
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_contact.getText().equals("联系主治医师")) {
                    Toast.makeText(MainActivity.this, "联系主治医师", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(MainActivity.this, "数值说明", Toast.LENGTH_SHORT).show();
                    digitalExplain();
                }

            }
        });
    }



    /**
     * 数值说明dialog
     */
    private void digitalExplain() {
        View digital_explain_dialog = getLayoutInflater().inflate(R.layout.dialog_digital_explain, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(digital_explain_dialog);
        final AlertDialog dialog = builder.show();
        // 背景设置透明
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置宽高
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
//        int height = outMetrics.heightPixels;
        dialog.getWindow().setLayout((int) (0.7 * width), WindowManager.LayoutParams.WRAP_CONTENT);

        TextView tv_digital_explain = digital_explain_dialog.findViewById(R.id.tv_digital_explain);
        tv_digital_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 更新图表
     * @param i 种类
     */
    private void updateChart(int i) {
        if(i==0){
            aaChartModel.subtitle("单位(%)")
                    .chartType(AAChartType.Area)
                    .yAxisMax(100f)
                    .categories(new String[]{"10","11","12","13","14","15","16","17","18","19","20","21","22"})
                    .series(new AASeriesElement[]{
                            new AASeriesElement()
                                    .name("疲劳检测曲线")
                                    .color("#5fb2f9")
                                    .data(new Object[]{5,10,30,35,25,25,30,38,35,45,40,55,69}),
                    });
        } else {
            aaChartModel.subtitle("数值(1-6)")
                    .chartType(AAChartType.Areaspline)
                    .yAxisMax(6f)
                    .categories(new String[]{"13时","14时","15时","16时","17时","18时","19时","20时","21时"})
                    .series(new AASeriesElement[]{
                            new AASeriesElement()
                                    .name("情感检测曲线")
                                    .color("#ffa226")
                                    .data(new Object[]{3,3,4,3,5,6,3,6,3}),
                    });

        }

        aaChartView.aa_refreshChartWithChartModel(aaChartModel);
    }
}