package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.GameBean;
import com.dyibing.myapp.mvp.model.UserGameModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.UserGameView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.RequestBody;
import rx.Subscription;

public class UserGamePresenter extends BasePresenter {

    private final UserGameModel mModel;

    public UserGamePresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new UserGameModel();
    }

    /**
     * 更新游戏个人信息
     */
    public void saveGameUser(RequestBody body) {
        Subscription subscription = mModel.saveGameUser(body,new ProgressSubscriber(o -> ((UserGameView) mView).saveGameUser((HttpResult) o), mContext));
        subList.add(subscription);
    }


    public void getGameUserId() {
        Subscription subscription = mModel.getGameUserId(new ProgressSubscriber(o -> ((UserGameView) mView).getGameUserId((GameBean) o), mContext,false));
        subList.add(subscription);
    }

}
