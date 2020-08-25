package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface ForestCoinService {
    /**
     * 领取森林币
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("childrenUser/receiveForestCoin")
    Observable<HttpResult> receiveForestCoin(@Body RequestBody body);


    /**
     * 用户前一天的任务是否完成
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("task/getUserFinishTaskStatus")
    Observable<HttpResult<FinishStatusBean>> getUserFinishTaskStatus();

    /**
     * 领取森林币
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("childrenUser/receiveForestCoinStatus")
    Observable<HttpResult<ForestCoinBean>> receiveForestCoinStatus();
}
