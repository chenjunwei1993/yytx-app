package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.net.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

public interface UserInfoService {
    /**
     * 获取用户信息
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("childrenUser/getUserInfo")
    Observable<HttpResult<UserInfoBean>> getUserInfo();
}
