package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.GameBean;
import com.dyibing.myapp.bean.UploadResult;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface UserGameService {
    /**
     * 游戏用户信息保存
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("childrenUser/saveGameUser")
    Observable<HttpResult> saveGameUser(@Body RequestBody body);


    /**
     * getGameUserId
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("childrenUser/getGameUserId")
    Observable<GameBean> getGameUserId();

}
