package com.example.bcicare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.HashMap;
import java.util.Map;

public class ReservationCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_code);

        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        getWindow().setStatusBarColor(Color.WHITE);

        initToolbar();

        ImageView ivBarcode = findViewById(R.id.iv_bar_code);
        ImageView ivQrcode = findViewById(R.id.iv_qr_code);
        TextView tv_code_number = findViewById(R.id.tv_code_number);

        String codeNumber = getCodeNumber();
        ivBarcode.setImageBitmap(createBarcode(codeNumber));
        ivQrcode.setImageBitmap(createQrcode(codeNumber));
        tv_code_number.setText(codeNumber);

    }

    /**
     * 初始化 toolbar
     */
    protected void initToolbar() {

        String title = "预约码";
        Toolbar toolbar = findViewById(R.id.include_toolbar);
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

    /**
     * 从服务器获取预约码
     *
     * @return 预约码
     */
    protected String getCodeNumber(){
        return "4241289378127321939";
    }

    /**
     * 生成条形码（不支持中文）
     *
     * @param content
     * @return
     */
    public static Bitmap createBarcode(String content) {
        try {
            // 生成BitMatrix(位矩阵)对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, 3000, 700);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            // 创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? 0xff000000 : 0xFFFFFFFF;
                }
            }
            // 创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成二维码
     *
     * @param content
     * @return
     */
    public static Bitmap createQrcode(String content) {
        // 设置二维码相关配置
        Map<EncodeHintType, Object> hints = new HashMap<>(2);
        // 支持中文配置
        // 字符转码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 容错级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            // 生成BitMatrix(位矩阵)对象
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 1000, 1000
                    , hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            // 创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = bitMatrix.get(x, y) ? 0xff000000 : 0xFFFFFFFF;
                }
            }
            // 创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}