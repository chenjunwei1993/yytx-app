package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface AnswerService {
    /**
     * 获取题目
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("/question/questionInfo")
    Observable<HttpResult<QuestionBean>> getQuestionInfo(String userGrade, String questionIds);

    /**
     * 答题
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("/question/submitQuestion")
    Observable<HttpResult> submitQuestion(@Body RequestBody body);
}
