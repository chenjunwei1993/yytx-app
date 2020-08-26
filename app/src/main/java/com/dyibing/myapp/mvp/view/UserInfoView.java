package com.dyibing.myapp.mvp.view;

import com.dyibing.myapp.bean.UserInfoBean;

public interface UserInfoView extends IBaseView{
    void onUserInfo(UserInfoBean userInfoBean);
}
