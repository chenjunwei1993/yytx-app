package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.FinishStatusBean;
import com.dyibing.myapp.mvp.model.ForestCoinModel;
import com.dyibing.myapp.mvp.view.ForestCoinView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class ForestCoinPresenter extends BasePresenter{

    private final ForestCoinModel mModel;

    public ForestCoinPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new ForestCoinModel();
    }

    /**
     * 获取用户前一天任务完成状态
     */

    public void getUserFinishTaskStatus() {
        Subscription subscription = mModel.getUserFinishTaskStatus(new ProgressSubscriber(o -> ((ForestCoinView) mView).onUserFinishTaskStatus((FinishStatusBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * 领取森林币
     */

    public void receiveForestCoin(RequestBody body) {
        Subscription subscription = mModel.receiveForestCoin(body,new ProgressSubscriber(o -> ((ForestCoinView) mView).onReceiveForestCoin((HttpResult) o), mContext));
        subList.add(subscription);
    }
}
