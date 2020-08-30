package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.mvp.service.FragmentService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class FragmentModel extends BaseModel {
    /**
     * 获取碎片
     *
     * @param subscriber
     * @return
     */
    public Subscription getFragment(Subscriber subscriber) {
        Observable<FragmentBean> observable = RetrofitHelper
                .getService(FragmentService.class)
                .getFragment()
                .map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    /**
     * 获取碎片列表
     *
     * @param subscriber
     * @return
     */
    public Subscription getFragmentList(String pageNo, Subscriber subscriber) {
        Observable<FragmentResult> observable = RetrofitHelper
                .getService(FragmentService.class)
                .getFragmentList(pageNo)
                .map(new HttpResultFunc<>());
        return toSubscribe(observable, subscriber);
    }

    /**
     * 兑换
     *
     * @param subscriber
     * @return
     */
    public Subscription fragmentSale(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(FragmentService.class)
                .fragmentSale(body);
        return toSubscribe(observable, subscriber);
    }
}
