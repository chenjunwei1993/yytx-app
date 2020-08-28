package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.mvp.model.LoginModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.LoginView;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class LoginPresenter extends BasePresenter {

    private final LoginModel mModel;

    public LoginPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new LoginModel();
    }


    /**
     * 登录
     */

    public void login(RequestBody body) {
        Subscription subscription = mModel.login(body,new ProgressSubscriber(o -> ((LoginView) mView).onLogin((LoginBean) o), mContext));
        subList.add(subscription);
    }
}
