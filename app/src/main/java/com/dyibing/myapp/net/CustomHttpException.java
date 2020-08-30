package com.dyibing.myapp.net;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dyibing.myapp.MyApplication;
import com.dyibing.myapp.R;
import com.dyibing.myapp.activity.SplashActivity;
import com.dyibing.myapp.common.Constant;


/**
 * 400	坏请求
 * 500	服务器错误
 * 503	服务当前无法处理请求
 */

public class CustomHttpException extends RuntimeException {

    public CustomHttpException(int code) {
        MyApplication.HttpStateCode = code;
        String errorMessage = getCustomHttpException(code);
//        RxBus.get().post(ConstantValue.EVENT_TYPE_NETWORK_EXCEPTION, errorMessage);
        throw new RuntimeException(errorMessage);
    }

    private String getCustomHttpException(int code) {
        Context context = MyApplication.getContext();
        switch (code) {
            case 401:
            case 403:
                SPUtils.getInstance(Constant.PREFERENCES_DB).put(Constant.USER_OPEN_ID, "");
                SPUtils.getInstance(Constant.PREFERENCES_DB).put(Constant.TOKEN, "");
                SPUtils.getInstance(Constant.PREFERENCES_DB).put(Constant.USER_GRADE, "");
                return context.getString(R.string.http_code_401);
            case 404:
                return context.getString(R.string.http_code_404);
            case 500:
                return context.getString(R.string.http_code_500);
            case 503:
                return context.getString(R.string.http_code_503);
            default:
        }
        return context.getString(R.string.http_code_default, code);
    }


}
