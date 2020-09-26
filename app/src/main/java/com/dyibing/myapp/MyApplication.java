package com.dyibing.myapp;

import android.app.Application;
import android.content.Context;


import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MyApplication extends MultiDexApplication {

    private static Context context;
    public static int HttpStateCode = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Utils.init(context);
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5f4e4e85");
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

}
