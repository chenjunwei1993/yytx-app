package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.QuestionBean;
import com.dyibing.myapp.mvp.service.AnswerService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class AnswerModel extends BaseModel {
    public Subscription submitQuestion(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(AnswerService.class)
                .submitQuestion(body);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getQuestionInfo(String userGrade, String questionIds, Subscriber subscriber) {
        Observable<QuestionBean> observable = RetrofitHelper
                .getService(AnswerService.class)
                .getQuestionInfo(userGrade, questionIds)
                .map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }
}
