package com.carolin_violet.travel_system.utils;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @ClassName BaiDuSentiment
 * @Description 百度情感分析接口
 * @Author zj
 * @Date 2022/6/4 19:43
 * @Version 1.0
 */
public class BaiDuSentiment {
    public static final String APP_ID = "26374241";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";

    public JSONObject getSentimentRes(String text) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();

        // 情感倾向分析
        JSONObject res = client.sentimentClassify(text, options);
        return res;
    }
}
