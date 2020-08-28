package com.dyibing.myapp.bean;

import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.dyibing.myapp.common.Constant;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据中心
 * Created by Alan on 20-7-10
 */

public class DataCenter {
    private static DataCenter instance;
    private static User mUser = new User();
    private static Map<String, String> mLotteryType = new HashMap<>();

    public static DataCenter getInstance() {
        if (instance == null) {
            instance = new DataCenter();
        }
        return instance;
    }

    public User getUser() {
        return mUser;
    }

    public String getToken() {
        if (TextUtils.isEmpty(mUser.getToken())) {
            mUser.setToken(SPUtils.getInstance(Constant.PREFERENCES_DB).getString(Constant.TOKEN));
        }
        return mUser.getToken();
    }

    public void setToken(String cookie) {
        mUser.setToken(cookie);
    }

    public void setUserId(String userId) {
        mUser.setUserId(userId);
    }

    public String getUserId() {
        if (TextUtils.isEmpty(mUser.getUserId())) {
            mUser.setUserId(SPUtils.getInstance(Constant.PREFERENCES_DB).getString(Constant.USER_OPEN_ID));
        }
        return mUser.getUserId();
    }

    public void setUserGrade(String userGrade){
        mUser.setUserGrade(userGrade);
    }

    public String getUserGrade(){
        if (TextUtils.isEmpty(mUser.getUserGrade())) {
            mUser.setUserGrade(SPUtils.getInstance(Constant.PREFERENCES_DB).getString(Constant.USER_GRADE));
        }
        return mUser.getUserGrade();
    }

}
