package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.WXTicketBean;
import com.dyibing.myapp.bean.WXTokenBean;
import com.dyibing.myapp.mvp.service.IWXAuthService;
import com.dyibing.myapp.net.RetrofitHelper;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class WXAuthModel extends BaseModel {
    public Subscription getWXTokenBean(String type, String appID, String appSecret, Subscriber subscriber) {
        Observable<WXTokenBean> observable = RetrofitHelper
                .getService(IWXAuthService.class)
                .getWXToken(type, appID, appSecret);
        return toSubscribe(observable, subscriber);
    }

    public Subscription getWXTicketBean(int type, String access_token, Subscriber subscriber) {
        Observable<WXTicketBean> observable = RetrofitHelper
                .getService(IWXAuthService.class)
                .getTicket(access_token, type);
        return toSubscribe(observable, subscriber);
    }
}
