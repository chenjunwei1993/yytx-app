package com.dyibing.myapp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.LoginBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.bean.WXTicketBean;
import com.dyibing.myapp.bean.WXTokenBean;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.mvp.presenter.LoginPresenter;
import com.dyibing.myapp.mvp.presenter.WXAuthPresenter;
import com.dyibing.myapp.mvp.view.LoginView;
import com.dyibing.myapp.mvp.view.WXAuthView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;

import java.util.HashMap;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AuthActivity extends AppCompatActivity implements WXAuthView, LoginView {
    @BindView(R.id.img_wx)
    ImageView imgWx;

    private IDiffDevOAuth oauth;
    private WXAuthPresenter wxAuthPresenter;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        oauth = DiffDevOAuthFactory.getDiffDevOAuth();
        loginPresenter = new LoginPresenter(this, this);
        wxAuthPresenter = new WXAuthPresenter(this, this);
        wxAuthPresenter.getWXTokenBean();
    }

    @OnClick({R.id.tv_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_refresh:
                wxAuthPresenter.getWXTokenBean();
                break;
        }
    }

    @Override
    public void onWXToken(WXTokenBean wxTokenBean) {
        String access_token = wxTokenBean.getAccess_token();
        if (!TextUtils.isEmpty(access_token)) {
            wxAuthPresenter.getWXTicketBean(access_token);
        }
    }

    @Override
    public void onWXTicket(WXTicketBean wxTicketBean) {
        String ticket = wxTicketBean.getTicket();
        if (!TextUtils.isEmpty(ticket)) {
            auth(ticket);
        }
    }

    private String getNonceStr() {
        Random r = new Random(System.currentTimeMillis());
        return EncryptUtils.encryptMD5ToString((Constant.APP_ID +
                r.nextInt(10000) + System.currentTimeMillis()));
    }

    private String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    private String getSignature(String noncestr, String timestamp, String sdk_ticket) {
        StringBuilder str = new StringBuilder();
        str.append("appid=" + Constant.APP_ID);
        str.append("&noncestr=" + noncestr);
        str.append("&sdk_ticket=" + sdk_ticket);
        str.append("&timestamp=" + timestamp);
        return EncryptUtils.encryptSHA1ToString(str.toString());
    }

    private OAuthListener oAuthListener = new OAuthListener() {
        @Override
        public void onAuthGotQrcode(String s, byte[] imgBuf) {
            if (imgBuf != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBuf, 0, imgBuf.length);
                runOnUiThread(() -> imgWx.setImageBitmap(bitmap));
            }
        }

        @Override
        public void onQrcodeScanned() {
            LogUtils.dTag("OAuthListener", "onQrcodeScanned");
        }

        @Override
        public void onAuthFinish(OAuthErrCode oAuthErrCode, String authCode) {
            LogUtils.dTag("OAuthListener", authCode);
            if (oAuthErrCode.getCode() == 0) {
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("code", authCode);
                String strEntity = new Gson().toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                loginPresenter.login(body);
            }
        }
    };

    private void auth(String ticket) {
        String nonceStr = getNonceStr();
        String timestamp = getTimestamp();
        String signature = getSignature(nonceStr, timestamp, ticket);

        try {
            oauth.removeListener(oAuthListener);
            oauth.auth(Constant.APP_ID, "snsapi_userinfo", nonceStr, timestamp, signature, oAuthListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        oauth.removeListener(oAuthListener);
        oauth = null;
        oAuthListener = null;
        wxAuthPresenter.onDestory();
    }

    @Override
    public void onLogin(LoginBean loginBean) {
        DataCenter.getInstance().setUserId(loginBean.getUserOpenId());
        DataCenter.getInstance().setToken(loginBean.getToken());
        SPUtils.getInstance(Constant.PREFERENCES_DB).put(Constant.TOKEN, loginBean.getToken());
        SPUtils.getInstance(Constant.PREFERENCES_DB).put(Constant.USER_OPEN_ID, loginBean.getUserOpenId());
        String receiveForestCoinStatus = loginBean.getReceiveForestCoinStatus();
        if ("noStock".equals(loginBean.getUserStockType())) {
            //新用户，第一次登录，不用判断是否完成任务 后台添加森林币业务
            SPUtils.getInstance(Constant.PREFERENCES_DB).put(Constant.FIRST_LOGIN, true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            if(TextUtils.equals("noReceive",receiveForestCoinStatus)){
                //显示森林币页面
                startActivity(new Intent(this, ForestCoinActivity.class));
            }else{
                //本地有领取记录 说明已经领取
                startActivity(new Intent(this, MainActivity.class));
            }
            finish();
        }
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {

    }

    @Override
    public void onReceiveForestCoinStatus(ForestCoinBean forestCoinBean) {

    }

}
