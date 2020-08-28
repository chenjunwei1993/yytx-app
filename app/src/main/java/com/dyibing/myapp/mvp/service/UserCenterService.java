package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.UploadResult;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface UserCenterService {
    /**
     * 用户信息保存
     *
     * @return
     */
//    @Headers("Content-Type:application/json")
    @POST("childrenUser/saveUser")
    Observable<HttpResult> saveUser(@Body RequestBody body);


    //图片上传
    @POST("file/upload/file")
    Observable<UploadResult> upload(@Body MultipartBody img);

}
