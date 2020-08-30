package com.dyibing.myapp.mvp.service;

import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.bean.SubmitQuestionBean;
import com.dyibing.myapp.net.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface AnswerService {
    /**
     * 获取题目
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("question/questionInfo")
    Observable<HttpResult<QuestionBean>> getQuestionInfo(
            @Query("userGrade") String userGrade,
            @Query("questionIds") String questionIds,
            @Query("batchNumber") String batchNumber);

    /**
     * 答题
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @POST("question/submitQuestion")
    Observable<HttpResult<SubmitQuestionBean>> submitQuestion(@Body RequestBody body);

    /**
     * 获取本轮答题获取的森林币个数
     *
     * @return
     */
    @Headers("Content-Type:application/json")
    @GET("question/queryForestCoinCountByBatchNumber")
    Observable<HttpResult<ForestCoinBean>> queryForestCoinCountByBatchNumber(
            @Query("batchNumber") String batchNumber);
}
