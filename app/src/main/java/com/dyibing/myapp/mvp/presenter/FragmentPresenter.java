package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.FragmentBean;
import com.dyibing.myapp.bean.FragmentResult;
import com.dyibing.myapp.mvp.model.FragmentModel;
import com.dyibing.myapp.mvp.view.FragmentView;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class FragmentPresenter extends BasePresenter {
    private final FragmentModel mModel;

    public FragmentPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new FragmentModel();
    }


    /**
     * 获取碎片
     */
    public void getFragment() {
        Subscription subscription = mModel.getFragment(new ProgressSubscriber(o -> ((FragmentView) mView).getFragment((FragmentBean) o), mContext, false));
        subList.add(subscription);
    }

    /**
     * 获取碎片列表
     */
    public void getFragmentList(String pageNO) {
        Subscription subscription = mModel.getFragmentList(pageNO, new ProgressSubscriber(o -> ((FragmentView) mView).getFragmentList((FragmentResult) o), mContext));
        subList.add(subscription);
    }

    /**
     * 兑换
     */
    public void fragmentSale(RequestBody body) {
        Subscription subscription = mModel.fragmentSale(body,
                new ProgressSubscriber(o -> ((FragmentView) mView).fragmentSale((HttpResult) o), mContext));
        subList.add(subscription);
    }
}
