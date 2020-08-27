package com.dyibing.myapp.net;


import android.content.Context;
import android.text.TextUtils;

import com.dyibing.myapp.BuildConfig;
import com.dyibing.myapp.MyApplication;
import com.dyibing.myapp.bean.DataCenter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alan on 2020-7-5.
 */

public class RetrofitHelper {
    private static final int DEFAULT_TIMEOUT_SECONDS = 7;
    private static final int DEFAULT_READ_TIMEOUT_SECONDS = 20;
    private static final int DEFAULT_WRITE_TIMEOUT_SECONDS = 20;
    private Retrofit mRetrofit;

    //开发环境
    String releaseHost="http://127.0.0.1:8001/yysgplatform/";
    //测试环境
    String debugHost="http://24:45:78:154/yyexploreApp";

    private RetrofitHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .addInterceptor(new ReceivedCookiesInterceptor())
//                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new LoggingInterceptor());
//        String domain = DataCenter.getInstance().getDomain();
//        builder.sslSocketFactory(new TlsSniSocketFactory(domain), new SSLUtil.TrustAllManager())
//                .hostnameVerifier(new TrueHostnameVerifier(domain));

        // HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //   日志拦截器
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        builder.addInterceptor(interceptor);  // 添加httplog

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(debugHost)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(new Gson())) //添加Gson支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //添加RxJava支持
                    .client(builder.build()) //关联okhttp
                    .build();
        }

    }

    private static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * kies) {
     * if(cookie.contains("SID=")&&c
     * 获取服务对象   Rxjava+Retrofit建立在接口对象的基础上的
     * 泛型避免强制转换
     */
    public static <T> T getService(Class<T> classz) {
        return RetrofitHelper.getInstance().mRetrofit.create(classz);
    }

    public Response responseS;

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            setHeaderToken(builder);
            setHeaderBaseParams(builder);
            request = builder.build();
            return processResponse(chain.proceed(request));
        }

        //访问网络之后，处理Response(这里没有做特别处理)
        private Response processResponse(Response response) {
            responseS = response;
            doHttpCode(response);
            refreshToken(response);
            refreshCookies(response);
            return response;
        }

        public Response getResponse() {
            return responseS;
        }

        /**
         * 设置token
         *
         * @param builder
         */
        private void setHeaderToken(Request.Builder builder) {
            String token = DataCenter.getInstance().getToken();
//            String token = "s4s4a5o38j0gre8wh74cyieksnqygbfy";
            if (!TextUtils.isEmpty(token)) {
                builder.addHeader("User-Token", token);
            }
        }

        /**
         * 设置基本参数
         *
         * @param builder
         */
        private void setHeaderBaseParams(Request.Builder builder) {
//            if (!BuildConfig.DEBUG) {
//                builder.addHeader("Host", DataCenter.getInstance().getDomain());
//            }
//            builder.addHeader("User-Agent", "app_android");
//            builder.addHeader("VersionName", PackageInfoUtil.getVersionName(BoxApplication.getContext()));//app版本号
//            builder.addHeader("SysCode", Build.VERSION.RELEASE);// 系统版本号
//            builder.addHeader("Brand", Build.BRAND);//手机品牌
//            builder.addHeader("Model", Build.MODEL);//手机型号
//            builder.addHeader("serialNo", DataCenter.getInstance().getSysInfo().getMac());
//            if (DataCenter.getInstance().getSysInfo().getSid() != null) {
//                builder.addHeader("Cookie", DataCenter.getInstance().getSysInfo().getSid());
//            }
//            builder.addHeader("Connection", "close"); //关闭链接
        }

        /**
         * 处理http code
         *
         * @param response
         */
        private void doHttpCode(Response response) {
            // 处理http code
            int statusCode = response.code();
            //code直接返回
//            RxBus.get().post(ConstantValue.RESPONSED_STATE_CODE, statusCode);
            if (statusCode != 200) {
                throw new CustomHttpException(statusCode);
            }
        }


        /**
         * 刷新token
         *
         * @param response
         */
        private void refreshToken(Response response) {
            // 处理token
//            Headers headers = response.headers();
//            String token = headers.get("token");
//            if (token != null && !token.equals(DataCenter.getInstance().getUser().getToken())) {
//                DataCenter.getInstance().getUser().setToken(token);
//            }
        }
    }

    /**
     * 保存cookies
     *
     * @param response
     */
    private void refreshCookies(Response response) {
        // 处理token
//        Headers headers = response.headers();
//        List<String> cookies = headers.values("Set-Cookie");
//        if (cookies != null && !cookies.isEmpty()) {
//            for (String cookie : cookies) {
//                if (cookie.contains("SID=") && cookie.length() > 80) {
//                    String[] sids = cookie.split(";");
//                    if (sids != null && sids.length > 0 && sids[0].length() > 80) {
////                        DataCenter.getInstance().getSysInfo().setSid(cookie);
//                        // CookieManager.getInstance().setCookie(DataCenter.getInstance().getDomain(), cookie);
//                        break;
//                    }
//                }
//            }
//        }
    }

}
