package com.dyibing.myapp.mvp.model;

import com.dyibing.myapp.bean.UploadResult;
import com.dyibing.myapp.mvp.service.UserCenterService;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.net.RetrofitHelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class UserCenterModel extends BaseModel {
//
//    public Subscription saveUser(String nickName,String birthday,String userSex,String userHobby,
//                                       String likeGift,
//                                      String likeCartoon,
//                                      String likeIdol,
//                                      String likeGame,
//                                      String avatarUrl,
//                                      String parentWxId,  Subscriber subscriber) {
//        Observable<HttpResult> observable = RetrofitHelper
//                .getService(UserCenterService.class)
//                .saveUser( nickName, birthday, userSex, userHobby,
//                         likeGift,
//                         likeCartoon,
//                         likeIdol,
//                         likeGame,
//                         avatarUrl,
//                         parentWxId);
//        return toSubscribe(observable, subscriber);
//    }
    public Subscription saveUser(RequestBody body, Subscriber subscriber) {
        Observable<HttpResult> observable = RetrofitHelper
                .getService(UserCenterService.class)
                .saveUser( body );
        return toSubscribe(observable, subscriber);
    }

    public Subscription upload(MultipartBody img, Subscriber subscriber) {
        Observable<UploadResult> observable = RetrofitHelper
                .getService(UserCenterService.class)
                .upload(img);
        return toSubscribe(observable, subscriber);
    }
}
