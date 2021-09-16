package com.example.bcicare.utils;

import android.content.Context;
import android.util.Log;


import com.baidu.paddle.lite.MobileConfig;
import com.baidu.paddle.lite.PaddlePredictor;
import com.baidu.paddle.lite.PowerMode;
import com.baidu.paddle.lite.Tensor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class PaddleLiteUtil {
    private static final String TAG = "PaddleLiteUtil";
    private Context context;
    private String modelName;
    private long[] dims;

    public PaddleLiteUtil(Context context) {
        this.context = context;
        this.modelName = "cnn_opt.nb";
        this.dims = new long[]{10, 1, 16, 1280};
    }


    public PaddleLiteUtil(Context context, String modelName){
        this.context = context;
        this.modelName = modelName;
        this.dims = new long[]{10, 1, 16, 1280};
    }

    public PaddleLiteUtil(Context context, String modelName, long[] dims){
        this.context = context;
        this.modelName = modelName;
        this.dims = dims;
    }


    public float[] getFloatResult(float[] inputBuffer) {
        String path = copyFromAssetsToCache("models", context);


        // 1. 写入配置：设置MobileConfig
        MobileConfig config = new MobileConfig();
        config.setModelFromFile(path + "/" + modelName); // 设置Paddle-Lite模型路径
        config.setPowerMode(PowerMode.LITE_POWER_NO_BIND); // 设置CPU运行模式
        config.setThreads(4); // 设置工作线程数

        // 2. 创建 PaddlePredictor
        PaddlePredictor predictor = PaddlePredictor.createPaddlePredictor(config);

        // 3. 设置输入数据
        Tensor inputTensor = predictor.getInput(0);
        inputTensor.resize(dims);
        inputTensor.setData(inputBuffer);


        // 4. 执行预测
        predictor.run();

        // 5. 获取输出数据
        Tensor result = predictor.getOutput(0);
        float[] output = result.getFloatData();
        System.out.println("shape:" + Arrays.toString(result.shape()));
        System.out.println("length: " + output.length);
        return output;
    }


    /**
     * 拷贝Assets下的文件到Cache
     *
     * @param modelPath 路径
     * @param context   上下文
     * @return 路径
     */
    public String copyFromAssetsToCache(String modelPath, Context context) {
        String newPath = context.getCacheDir() + "/" + modelPath;
        // String newPath = "/sdcard/" + modelPath;
        File desDir = new File(newPath);

        try {
            if (!desDir.exists()) {
                desDir.mkdir();
            }
            for (String fileName : context.getAssets().list(modelPath)) {
                InputStream stream = context.getAssets().open(modelPath + "/" + fileName);
                OutputStream output = new BufferedOutputStream(new FileOutputStream(newPath + "/" + fileName));

                byte[] data = new byte[1024];
                int count;

                while ((count = stream.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                stream.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return desDir.getPath();
    }
}
