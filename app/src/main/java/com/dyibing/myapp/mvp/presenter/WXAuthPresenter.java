package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.WXTicketBean;
import com.dyibing.myapp.bean.WXTokenBean;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.mvp.model.WXAuthModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.WXAuthView;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import rx.Subscription;

public class WXAuthPresenter extends BasePresenter {
    private final WXAuthModel mModel;

    public WXAuthPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new WXAuthModel();
    }

    /**
     * token信息
     */

    public void getWXTokenBean() {
        Subscription subscription = mModel.getWXTokenBean("client_credential", Constant.APP_ID, Constant.APP_SECRET,
                new ProgressSubscriber(o -> ((WXAuthView) mView).onWXToken((WXTokenBean) o), mContext));
        subList.add(subscription);
    }

    /**
     * Ticket信息
     */

    public void getWXTicketBean(String access_token) {
        Subscription subscription = mModel.getWXTicketBean(2, access_token,
                new ProgressSubscriber(o -> ((WXAuthView) mView).onWXTicket((WXTicketBean) o), mContext));
        subList.add(subscription);
    }

}
