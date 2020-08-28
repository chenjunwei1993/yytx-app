package com.dyibing.myapp.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.UserInfoBean;
import com.dyibing.myapp.listener.OnMultiClickListener;
import com.dyibing.myapp.mvp.presenter.UserInfoPresenter;
import com.dyibing.myapp.mvp.view.UserInfoView;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Descripttion: 主页
 * @Author: chenjunwei
 * @Date: 2020/8/26
 */
public class MainActivity extends AppCompatActivity implements UserInfoView {

    @BindView(R.id.circle_avatar)
    CircleImageView circleAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_forest_coin_Count)
    TextView tvForestCoinCount;

    private UserInfoPresenter userInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        AudioUtils.getInstance().init(this); //初始化语音对象
        userInfoPresenter = new UserInfoPresenter(this, this);
        userInfoPresenter.getUserInfo();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Utils.fullScreen(getWindow());
    }

    @Override
    protected void onResume() {
        super.onResume();
        userInfoPresenter.getUserInfo();
    }

    @OnClick({R.id.circle_avatar, R.id.iv_gold, R.id.iv_fragment})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.circle_avatar:
                startActivity(new Intent(this, UserCenterActivity.class));
                break;
            case R.id.iv_gold:
                startActivity(new Intent(MainActivity.this, StartAnswerActivity.class));
                break;
            case R.id.iv_fragment:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userInfoPresenter.onDestory();
    }

    @Override
    public void onUserInfo(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return;
        }
        DataCenter.getInstance().setUserGrade(userInfoBean.getUserGrade());
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
}
