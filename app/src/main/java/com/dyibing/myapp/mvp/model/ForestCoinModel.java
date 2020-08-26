package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.mvp.service.ForestCoinService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class ForestCoinModel extends BaseModel {
    public Subscription receiveForestCoin(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(ForestCoinService.class)
                .receiveForestCoin(body);
        return toSubscribe(observable, subscriber);
    }

}
