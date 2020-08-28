package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.model.LoginModel;
import com.dyibing.myapp.mvp.model.UserInfoModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.LoginView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import rx.Subscription;

public class UserInfoPresenter extends BasePresenter {
    private final UserInfoModel mModel;

    public UserInfoPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new UserInfoModel();
    }

    /**
     * 获取用户信息
     */

    public void getUserInfo() {
        Subscription subscription = mModel.getUserInfo(new ProgressSubscriber(o -> ((UserInfoView) mView).onUserInfo((UserInfoBean) o), mContext));
        subList.add(subscription);
    }

}
