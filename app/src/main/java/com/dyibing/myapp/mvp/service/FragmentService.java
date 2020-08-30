package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface FragmentService {
    /**
     * 获取本轮答题获取的森林币个数
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("fragment/getFragment")
    Observable<HttpResult<FragmentBean>> getFragment();

    /**
     * 获取碎片列表
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("fragment/getFragmentList")
    Observable<HttpResult<FragmentResult>> getFragmentList(
            @Query("pageNo") String pageNo);

    /**
     * 兑换
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("fragment/fragmentSale")
    Observable<HttpResult> fragmentSale(@Body RequestBody body);


}
