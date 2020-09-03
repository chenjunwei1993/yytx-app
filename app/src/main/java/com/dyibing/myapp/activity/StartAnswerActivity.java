package com.dyibing.myapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.ForestCoinBean;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.mvp.presenter.ForestCoinPresenter;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.ForestCoinView;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @Descripttion: 开始答题 提示页
 * @Author: 陈俊伟
 * @Date: 2020/8/28
 */
public class StartAnswerActivity extends AppCompatActivity implements UserInfoView, ForestCoinView {
    @BindView(R.id.circle_avatar)
    CircleImageView circleAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_forest_coin_count)
    TextView tvForestCoinCount;

    private UserInfoPresenter userInfoPresenter;
    private ForestCoinPresenter forestCoinPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_answer);
        ButterKnife.bind(this);
        AudioUtils.getInstance().speakText(getString(R.string.start_answer_tip));
        userInfoPresenter = new UserInfoPresenter(this, this);
        userInfoPresenter.getUserInfo();
        forestCoinPresenter = new ForestCoinPresenter(this, this);
    }

    @OnClick({R.id.circle_avatar, R.id.iv_start_answer, R.id.ll_back})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.circle_avatar:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_start_answer:
                HashMap<String, Object> paramsMap = new HashMap<>();
                paramsMap.put("type", "GAME");
                String strEntity = new Gson().toJson(paramsMap);
                RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
                forestCoinPresenter.useForestCoin(body);
                break;
        }
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }
        Utils.setText(String.valueOf(userInfoBean.getForestCoinCount()), tvForestCoinCount);
        if (!TextUtils.isEmpty(userInfoBean.getAvatarUrl())) {
            Glide.with(this).load(userInfoBean.getAvatarUrl()).into(circleAvatar);
        }
        if (!TextUtils.isEmpty(userInfoBean.getNickName())) {
            tvName.setText(userInfoBean.getNickName());
        } else {
            tvName.setText(userInfoBean.getUserId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }

    @Override
    public void onReceiveForestCoin(HttpResult httpResult) {

    }

    @Override
    public void onReceiveForestCoinStatus(ForestCoinBean forestCoinBean) {

    }

    @Override
    public void onUseForestCoin(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                startActivity(new Intent(this, AnswerActivity.class));
                finish();
            } else {
                SingleToast.showMsg(httpResult.getMsg());
            }
        }
    }
}
