package com.dyibing.myapp.net;

import android.content.Context;

import com.blankj.utilcode.util.ActivityUtils;
import com.dyibing.myapp.MyApplication;
import com.dyibing.myapp.R;


/**

 * 400	坏请求
 * 500	服务器错误
 * 503	服务当前无法处理请求
 */

public class CustomHttpException extends RuntimeException {

    public CustomHttpException(int code) {
        MyApplication.HttpStateCode=code;
        String errorMessage = getCustomHttpException(code);
//        RxBus.get().post(ConstantValue.EVENT_TYPE_NETWORK_EXCEPTION, errorMessage);
        throw new RuntimeException(errorMessage);
    }

    private String getCustomHttpException(int code) {
        Context context = MyApplication.getContext();
        switch (code) {
            case 401:
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
