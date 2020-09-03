package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.GameBean;
import com.dyibing.myapp.mvp.service.UserGameService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class UserGameModel extends BaseModel {
    public Subscription saveGameUser(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(UserGameService.class)
                .saveGameUser(body);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getGameUserId(Subscriber subscriber) {
        Observable<GameBean> observable = RetrofitHelper
                .getService(UserGameService.class)
                .getGameUserId();
        return toSubscribe(observable, subscriber);
    }
}
