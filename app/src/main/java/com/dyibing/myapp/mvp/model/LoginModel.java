package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.service.ForestCoinService;
import com.dyibing.myapp.mvp.service.LoginService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class LoginModel extends BaseModel {

    public Subscription login(RequestBody body, Subscriber subscriber) {
        Observable<LoginBean> observable = RetrofitHelper
                .getService(LoginService.class)
                .login(body)
                .map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    public Subscription getUserInfo(Subscriber subscriber) {
        Observable<UserInfoBean> observable = RetrofitHelper
                .getService(LoginService.class)
                .getUserInfo()
                .map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    public Subscription receiveForestCoinStatus(Subscriber subscriber) {
        Observable<ForestCoinBean> observable = RetrofitHelper
                .getService(ForestCoinService.class)
                .receiveForestCoinStatus()
                .map(new HttpResultFunc<>());;
        return toSubscribe(observable, subscriber);
    }
}
