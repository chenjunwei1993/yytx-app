package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface LoginService {


    /**
     * 登录
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("user/login")
    Observable<HttpResult<LoginBean>> login(@Body RequestBody body);

    /**
     * 获取用户信息
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("childrenUser/getUserInfo")
    Observable<HttpResult<UserInfoBean>> getUserInfo();

}
