package com.dyibing.myapp.mvp.presenter;

import android.content.Context;

import com.dyibing.myapp.bean.UploadResult;
import com.dyibing.myapp.mvp.model.UserCenterModel;
import com.dyibing.myapp.mvp.view.IBaseView;
import com.dyibing.myapp.mvp.view.UserCenterView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.rx.ProgressSubscriber;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscription;

public class UserCenterPresenter extends BasePresenter {

    private final UserCenterModel mModel;

    public UserCenterPresenter(Context mContext, IBaseView view) {
        super(mContext, view);
        mModel = new UserCenterModel();
    }



//    /**
//     * 更新个人信息
//     */
//
//    public void saveUser(String nickName,String birthday,String userSex,String userHobby,
//                                   String likeGift,
//                                   String likeCartoon,
//                                   String likeIdol,
//                                   String likeGame,
//                                   String avatarUrl,
//                                   String parentWxId) {
//        Subscription subscription = mModel.saveUser(nickName, birthday, userSex, userHobby,
//                likeGift,
//                likeCartoon,
//                likeIdol,
//                likeGame,
//                avatarUrl,
//                parentWxId,new ProgressSubscriber(o -> ((UserCenterView) mView).onUpdateCurrentDateTask((HttpResult) o), mContext));
//        subList.add(subscription);
//    }

    /**
     * 更新个人信息
     */

    public void saveUser(RequestBody body) {
        Subscription subscription = mModel.saveUser(body,new ProgressSubscriber(o -> ((UserCenterView) mView).onUpdateCurrentDateTask((HttpResult) o), mContext));
        subList.add(subscription);
    }

    //上传图片

    public void upload(MultipartBody img) {
        Subscription subscription = mModel.upload(img,new ProgressSubscriber(o -> ((UserCenterView) mView).onUpload((UploadResult) o), mContext));
        subList.add(subscription);
    }

}
