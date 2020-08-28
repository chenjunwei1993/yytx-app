package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.service.UserInfoService;
import com.dyibing.myapp.net.RetrofitHelper;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class UserInfoModel extends BaseModel{

    public Subscription getUserInfo(Subscriber subscriber) {
        Observable<UserInfoBean> observable = RetrofitHelper
                .getService(UserInfoService.class)
                .getUserInfo()
                .map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }
}
